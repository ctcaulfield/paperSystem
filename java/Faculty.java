import java.util.ArrayList;

/**
 * 
 * @author Ian Kitchen <igk2718@rit.edu>
 * 
 * A Java object representing an entry in the faculty table
 */
public class Faculty {
	
	private int facultyId;
	private String fName;
	private String lName;
	private String password;
	private String email;
	private ArrayList<Papers> papers;
	private MySQLDatabase mysqldb;
	
	/**
	 * Default constructor
	 */
	public Faculty() {
		mysqldb = new MySQLDatabase();
	}//END constructor
	
	/**
	 * Constructor that sets a default faculty id attribute
	 * 
	 * @param facultyId
	 */
	public Faculty(int facultyId) {
		this.facultyId = facultyId;
		mysqldb = new MySQLDatabase();
	}
	
	/**
	 * A constructor that sets all the attributes
	 * 
	 * @param facultyId - the faculty id number
	 * @param fName - the faculty's first name
	 * @param lName - the faculty's last name
	 * @param password - the password for the faculty to log in
	 * @param email - the email address of the faculty
	 */
	public Faculty(int facultyId, String fName, String lName, String password,
			String email) {
		this.facultyId = facultyId;
		this.fName = fName;
		this.lName = lName;
		this.password = password;
		this.email = email;
		mysqldb = new MySQLDatabase();
	}
	
	/**
	 * Gets the id of the faculty
	 * 
	 * @return the current id of the faculty
	 */
	public int getId() {
		return facultyId;
	}
	
	/**
	 * Sets the current id attribute to a new id
	 * 
	 * @param facultyId - the new id attribute
	 */
	public void setId(int facultyId) {
		this.facultyId = facultyId;
	}
	
	/**
	 * Gets the first name of the faculty
	 * 
	 * @return the first name of the faculty
	 */
	public String getFirstName() {
		return fName;
	}
	
	/**
	 * Sets the current first name attribute to a new name
	 * 
	 * @param fName - the new first name of the faculty
	 */
	public void setFirstName(String fName) {
		this.fName = fName;
	}
	
	/**
	 * Gets the last name of the faculty
	 * 
	 * @return the last name of the faculty
	 */
	public String getLastName() {
		return lName;
	}
	
	/**
	 * Sets the last name attribute to a new name
	 * 
	 * @param lName - the new last name of the faculty
	 */
	public void setLastName(String lName) {
		this.lName = lName;
	}
	
	/**
	 * Gets the current password of the faculty
	 * 
	 * @return the current password for this faculty
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password attribute to a new password
	 * 
	 * @param password - the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the email address of the faculty
	 * 
	 * @return the email address of the faculty
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email attribute to a new email address
	 * 
	 * @param email - the new email address
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Gets the entire list of papers authored
	 * 
	 * @return the list of authored papers
	 */
	public ArrayList<Papers> getPapers() {
		return papers;
	}
	
	/**
	 * Sets the current list of papers to a completely new list
	 * 
	 * @param papers - the new list of papers
	 */
	public void setPapers(ArrayList<Papers> papers) {
		this.papers = papers;
	}
	
	/**
	 * Adds a single paper to the current list of papers
	 * 
	 * @param newPaper - the new paper to be added
	 */
	public void addPaper(Papers newPaper) {
		papers.add(newPaper);
	}
	
	/**
	 * fetch() will use this object's paperId attribute and the 
	 * MySQLDatabase class' getData() method to retrieve the database values 
	 * for this particular paperId and update the other attributes 
	 * accordingly. 
	 * 
	 * **NOTE** For this fetch, be sure to run the populate keywords and authors
	 *          methods after fetch() so that those arrays are up to date as 
	 *          well
	 *          
	 * @return true if the fetch updated data
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean fetch() {
		
		boolean doesFetch = false;
		
		//USE this string for a correct select statement
        String stmnt = "SELECT fname, lname, password, email FROM faculty WHERE "
        		+ "id = ?";
        ArrayList<String> values = new ArrayList<>(1);
        values.add(Integer.toString(facultyId));
        
        try {
			mysqldb.connect();
		    //execute the SELECT statement and get the results
		    ArrayList<ArrayList> data = mysqldb.getData(stmnt, values);
			
			//loop through the data array and set the attributes
		    if(data.size() != 0) {
				for(ArrayList<String> d: data) {	
					for(int i=0; i < d.size(); i++) {
						if(i == 3)
							setEmail(d.get(i));
						
						else if(i == 2)
						    setPassword(d.get(i));
						
						else if(i == 1)
							setLastName(d.get(i));
						
						else
							setFirstName(d.get(i));
					}
				}
				doesFetch = true;
			}
		    else {
		    	//the data returned an empty set, set all attributes to defaults
		    	setId(0);
		    	setFirstName("");
		    	setLastName("");
		    	setPassword("");
		    	setEmail("");
		    }
		    mysqldb.close();
				
		} catch (DLException e) {
			System.out.println("Could not complete operation. "
					+ "Please check log file");
		}
        
        return doesFetch;
	}//END fetch
	
	/**
	 * put() will update the database values, for this object's facultyId, 
	 * using all this object's attributes (fName, lName, password, email).
	 */
	public void put() {
		String stmnt = "UPDATE faculty SET fname = ?, lname = ?, password = ?, "
				+ "email = ? WHERE id = ?";
		ArrayList<String> values = new ArrayList<>(5);
		values.add(fName);
		values.add(lName);
		values.add(password);
		values.add(email);
		values.add(Integer.toString(facultyId));
		
		try {
			mysqldb.connect();
			mysqldb.setData(stmnt,values);
			mysqldb.close();
				
		} catch (DLException e) {
			System.out.println("Could not complete operation. "
					+ "Please check log file");
		}		
	}//END put
	
	/**
	 * post() will insert this object's attribute values as a new record into 
	 * the database table.
	 */
	public void post() {
		String stmnt = "INSERT INTO faculty VALUES (?,?,?,?,?)";
		ArrayList<String> values = new ArrayList<>(5);
		values.add(Integer.toString(facultyId));
		values.add(fName);
		values.add(lName);
		values.add(password);
		values.add(email);
		
		try {
			mysqldb.connect();
			mysqldb.setData(stmnt, values);
			mysqldb.close();
				
		} catch (DLException e) {
			System.out.println("Could not complete operation. "
					+ "Please check log file");
		}
	}//END post
	
	/**
	 * delete() will remove from the database table any data corresponding to 
	 * this object's facultyId
	 */
	public void delete() {
		String stmnt = "DELETE FROM faculty WHERE id = ?";
		ArrayList<String> values = new ArrayList<>(1);
		values.add(Integer.toString(facultyId));
		
		try {
			mysqldb.connect();
			mysqldb.setData(stmnt, values);
			mysqldb.close();
				
		} catch (DLException e) {
			System.out.println("Could not complete operation. "
					+ "Please check log file");
		}	
	}//END delete
	
}
