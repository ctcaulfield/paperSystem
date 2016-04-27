
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
	private MySQLDatabase mysqldb;
	
	/**
	 * Default constructor
	 */
	public Papers() {
		mysqldb = new MySQLDatabase();
	}//END constructor
	
	/**
	 * Constructor that sets a default paper ID attribute
	 * 
	 * @param paperId - the id of the paper
	 */
	public Papers(int paperId) {
		this.paperId = paperId;
		
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
}
