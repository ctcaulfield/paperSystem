import java.util.ArrayList;

/**
 * @author Ian Kitchen
 * 
 * This is the main class for opening and closing the MySQL Database as well as
 * running the methods that add and modify the data in the database
 */
public class SQLMain {
	
	private static MySQLDatabase msqldb;
	private static ArrayList<Faculty> faculty;
	private static ArrayList<Papers> research;

	public static void main(String[] args) {
		
		/************************* Start **************************/
		
		msqldb = new MySQLDatabase();
		faculty = populateFaculty(msqldb);
		research = populatePapers(msqldb);
		papersToAuthors(msqldb);
		populateKeywords(msqldb);
		for(Faculty f: faculty) {
			ArrayList<Papers> authored = f.getPapers();
			for(Papers p: authored) {
				System.out.println("ID: " + p.getId());
			}
			System.out.println();
		}
		for(Papers p: research) {
			System.out.println(p.getId() + ": " + p.getKeywords());
			System.out.print("Authors: ");
			for(Faculty f: p.getAuthors()) {
				System.out.print(f.getLastName() + ". ");
			}
			System.out.println("\n");
		}
			
	}//END main
	
	/**
	 * Use this to test login
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ArrayList<Faculty> populateFaculty(MySQLDatabase sql) {
		
		ArrayList<Faculty> faculty = new ArrayList<>();
		
		try {
			sql.connect();
			
			//create the SQL statement and execute it
			String stmnt = "SELECT * FROM faculty";
			ArrayList<ArrayList> resultsTable = sql.getData(stmnt);
			
			//for each row of data returned, create a new faculty object and
			//store it in an array
			for(ArrayList<String> l: resultsTable) {
				int id = Integer.parseInt(l.get(0));
				String fName = l.get(1);
				String lName = l.get(2);
				String password = l.get(3);
				String email = l.get(4);
				Faculty newF = new Faculty(id, fName, lName, password, email);
				faculty.add(newF);			
			}
			sql.close();
			
		} catch (DLException e) {
			e.printStackTrace();
		}
		return faculty;
	}
	
	/**
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ArrayList<Papers> populatePapers(MySQLDatabase sql) {
		
		ArrayList<Papers> research = new ArrayList<>();
		
		try {
			sql.connect();
			
			//create an SQL statement and execute it
			String stmnt = "SELECT * FROM papers";
			ArrayList<ArrayList> resultsTable = sql.getData(stmnt);
			
			//for each row of the data returned, create a paper object and store
			//it in an array
			for(ArrayList<String> l: resultsTable) {
				int id = Integer.parseInt(l.get(0));
				String title = l.get(1);
				String pAbstract = l.get(2);
				String citation = l.get(3);
				Papers paper = new Papers(id, title, pAbstract, citation);
				research.add(paper);
			}
			sql.close();
			
		} catch (DLException e) {
			e.printStackTrace();
		}
		return research;
	}
	
	/**
	 * 
	 * @param sql
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private static void papersToAuthors(MySQLDatabase sql) {
		try {
			//FOR each faculty object
			for(Faculty facultyObj: faculty) {
				//list of papers for this faculty object
				ArrayList<Papers> authored = new ArrayList<>();
				sql.connect();
				
				//create the SQL statement and execute it
				String stmnt = "SELECT papers.id FROM papers JOIN authorship ON "
						+ "papers.id = paperid JOIN faculty ON facultyid = "
						+ "faculty.id WHERE faculty.id = "+facultyObj.getId();
				ArrayList<ArrayList> paperList = sql.getData(stmnt);
				
				//for each row in the data set returned
				for(ArrayList<String> dataSet: paperList) {
					//for each paper object
					for(Papers paperObj: research) {
						//IF the paperID in the data set is equal to the current
						//paper object's ID, then the current faculty object is
						//the author of the paper
						if(Integer.parseInt(dataSet.get(0)) == paperObj.getId()) {
							authored.add(paperObj);
							
							//add author to the paper object's authors array
							paperObj.addAuthor(facultyObj);
							break;
						}
					}
				}
				//set the current the papers authored attribute of the faculty 
				//object
				facultyObj.setPapers(authored);
				sql.close();
			}
		} catch (DLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param sql
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void populateKeywords(MySQLDatabase sql) {
		try {
			sql.connect();
			for(Papers paperObj: research) {
				ArrayList<String> newKeywords = new ArrayList<>();
				
				//create the sql statement and execute it
				String stmnt = "SELECT keyword FROM paper_keywords JOIN papers"
						+ " ON paper_keywords.id = papers.id WHERE papers.id "
						+ "= "+paperObj.getId();
				ArrayList<ArrayList> dataSet = sql.getData(stmnt);
				
				//FOR each row in the data set
				for(ArrayList<String> keywords: dataSet) {
					//get the keywords and add them to a new keyword list
					for(String s: keywords) {
						newKeywords.add(s);
					}
				}
				//add the list of keywords to the paper object
				paperObj.setKeywords(newKeywords);
			}
		} catch (DLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}//END SQLMain
