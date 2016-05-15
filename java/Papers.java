import java.util.ArrayList;

/**
 * 
 * @author Ian Kitchen <igk2718@rit.edu>
 * 
 * A Java object representation of an entry in the papers table
 */
public class Papers {
	
	private int paperId;
	private String title;
	private String pAbstract;
	private String citation;
	private ArrayList<String> keywords;
	private ArrayList<Faculty> authors;
	private MySQLDatabase mysqldb;
	
	/**
	 * Default constructor
	 */
	public Papers() {
		mysqldb = new MySQLDatabase();
		authors = new ArrayList<>();
		keywords = new ArrayList<>();
		
	}//END constructor
	
	/**
	 * Constructor that sets a default paper ID attribute
	 * 
	 * @param paperId - the id of the paper
	 */
	public Papers(int paperId) {
		this.paperId = paperId;
		mysqldb = new MySQLDatabase();
		authors = new ArrayList<>();
		keywords = new ArrayList<>();
		
	}//END contructor2
	
	/**
	 * Constructor that sets all of the attributes
	 * 
	 * @param paperId - the id of the paper
	 * @param title - the title of the paper
	 * @param pAbstract - a brief summary of the paper
	 * @param citation - citations for the paper
	 */
	public Papers(int paperId, String title, String pAbstract, 
			String citation) {
		this.paperId = paperId;
		this.title = title;
		this.pAbstract = pAbstract;
		this.citation = citation;
		mysqldb = new MySQLDatabase();
		authors = new ArrayList<>();
		keywords = new ArrayList<>();
		
	}//END constructor3
	
	/**
	 * Gets the id of this paper
	 * 
	 * @return the current id of the paper
	 */
	public int getId() {
		return paperId;
	}
	
	/**
	 * Sets the id attribute of this paper
	 * 
	 * @param paperId - the new id of the paper
	 */
	public void setId(int paperId) {
		this.paperId = paperId;
	}
	
	/**
	 * Gets the title of the paper
	 * 
	 * @return the title of the paper
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title attribute of this paper
	 * 
	 * @param title - the new title of the paper
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the abstract of the paper
	 * 
	 * @return the abstract of the paper
	 */
	public String getAbstract() {
		return pAbstract;
	}
	
	/**
	 * Sets the abstract attribute of this paper
	 * 
	 * @param pAbstract - the new abstract of the paper
	 */
	public void setAbstract(String pAbstract) {
		this.pAbstract = pAbstract;
	}
	
	/**
	 * Gets the citation of this paper
	 * 
	 * @return the citation of this paper
	 */
	public String getCitation() {
		return citation;
	}
	
	/**
	 * Sets the citation attribute of this paper
	 * 
	 * @param citation - the new citation of the paper
	 */
	public void setCitation(String citation) {
		this.citation = citation;
	}
	
	/**
	 * Returns the list of authors for this paper
	 * 
	 * @return the list of authors
	 */
	public ArrayList<Faculty> getAuthors() {
		return authors;
	}
	
	/**
	 * Sets the current authors list to a new list of authors
	 * 
	 * @param authors - the new list of authors
	 */
	public void setAuthors(ArrayList<Faculty> authors) {
		this.authors = authors;
	}
	
	/**
	 * Adds a single author to the current list of authors
	 * 
	 * @param newAuthor - the new author to be added to the list
	 */
	public void addAuthor(Faculty newAuthor) {
		authors.add(newAuthor);
	}
	
	/**
	 * Gets the whole list of keywords
	 * 
	 * @return - the list of keywords
	 */
	public ArrayList<String> getKeywords() {
		return keywords;
	}
	
	/**
	 * Sets the current list of keywords to a new list
	 * 
	 * @param keywords - the new list of keywords
	 */
	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}
	
	/**
	 * Adds a single keyword to the keywords list
	 * 
	 * @param keyword - the new keyword to be added
	 */
	public void addKeyword(String keyword) {
		keywords.add(keyword);
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
        String stmnt = "SELECT title, abstract, citation FROM papers WHERE "
        		+ "id = ?";
        ArrayList<String> values = new ArrayList<>(1);
        values.add(Integer.toString(paperId));
        
        try {
			mysqldb.connect();
		    //execute the SELECT statement and get the results
		    ArrayList<ArrayList> data = mysqldb.getData(stmnt, values);
			
			//loop through the data array and set the attributes
		    if(data.size() != 0) {
				for(ArrayList<String> d: data) {	
					for(int i=0; i < d.size(); i++) {
						if(i == 2)
							setCitation(d.get(i));
						
						else if(i == 1)
						    setAbstract(d.get(i));
						
						else
							setTitle(d.get(i));
					}
				}
				doesFetch = true;
			}
		    else {
		    	//the data returned an empty set, set all attributes to defaults
		    	setId(0);
		    	setTitle("");
		    	setAbstract("");
		    	setCitation("");
		    }
		    mysqldb.close();
				
		} catch (DLException e) {
			System.out.println("Could not complete operation. "
					+ "Please check log file");
		}
        
        return doesFetch;
	}//END fetch
	
	/**
	 * put() will update the database values, for this object's paperId, 
	 * using all this object's attributes (title, abstract, citation).
	 */
	public void put() {
		String stmnt = "UPDATE papers SET title = ?, abstract = ?, citation = ?" 
				+ " WHERE id = ?";
		ArrayList<String> values = new ArrayList<>(4);
		values.add(title);
		values.add(pAbstract);
		values.add(citation);
		values.add(Integer.toString(paperId));
		
		try {
			mysqldb.connect();
			mysqldb.setData(stmnt, values);
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
		String stmnt = "INSERT INTO papers VALUES (?,?,?,?)";
		String keyStmnt = "INSERT INTO paper_keywords VALUES (?,?)";
		ArrayList<String> values = new ArrayList<>(4);
		ArrayList<String> keyValues;
		values.add(Integer.toString(paperId));
		values.add(title);
		values.add(pAbstract);
		values.add(citation);
		
		try {
			mysqldb.connect();
			mysqldb.startTrans();
			mysqldb.setData(stmnt, values);
			
			//loop through keywords and insert them into the keywords table
			for(String keyword: keywords) {
				keyValues = new ArrayList<>(2);
				keyValues.add(Integer.toString(paperId));
				keyValues.add(keyword);
				
				mysqldb.setData(keyStmnt, keyValues);
			}
			
			mysqldb.endTrans();
			mysqldb.close();
				
		} catch (DLException e) {
			System.out.println("Could not complete operation. "
					+ "Please check log file");
		}
	}//END post
	
	/**
	 * delete() will remove from the database table any data corresponding to 
	 * this object's paperId
	 */
	public void delete() {
		String stmnt = "DELETE FROM papers WHERE id = " + paperId;
		String keyStmnt = "DELETE FROM paper_keywords WHERE id = " + paperId;
		
		try {
			mysqldb.connect();
			mysqldb.startTrans();
			
			//delete keywords first
			mysqldb.setData(keyStmnt);
			
			//then delete the paper
			mysqldb.setData(stmnt);
			
			mysqldb.endTrans();
			mysqldb.close();
				
		} catch (DLException e) {
			System.out.println("Could not complete operation. "
					+ "Please check log file");
		}	
	}//END delete
}

