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
	
}
