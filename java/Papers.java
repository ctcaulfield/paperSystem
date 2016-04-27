
/**
 * 
 * @author Ian Kitchen <igk2718@rit.edu>
 * 
 * A Java object representation of the papers table in the database
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
	 * @param paperId
	 */
	public Papers(int paperId) {
		this.paperId = paperId;
		
	}//END contructor2
	
	public Papers(int paperId, String title, String pAbstract, 
			String citation) {
		this.paperId = paperId;
		this.title = title;
		this.pAbstract = pAbstract;
		this.citation = citation;
		mysqldb = new MySQLDatabase();
		
	}//END constructor3
	
	/**
	 * 
	 * @return
	 */
	public int getId() {
		return paperId;
	}
	
	/**
	 * 
	 * @param paperId
	 */
	public void setId(int paperId) {
		this.paperId = paperId;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getAbstract() {
		return pAbstract;
	}
	
	/**
	 * 
	 * @param pAbstract
	 */
	public void setAbstract(String pAbstract) {
		this.pAbstract = pAbstract;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCitation() {
		return citation;
	}
	
	/**
	 * 
	 * @param citation
	 */
	public void setCitation(String citation) {
		this.citation = citation;
	}
}
