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
}

