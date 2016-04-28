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
		for(Faculty f: faculty) {
			ArrayList<Papers> authored = f.getPapers();
			for(Papers p: authored) {
				System.out.println("ID: " + p.getId());
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
			
			String stmnt = "SELECT * FROM faculty";
			
			ArrayList<ArrayList> resultsTable = sql.getData(stmnt);
			
			for(ArrayList<String> l: resultsTable) {
				int id = Integer.parseInt(l.get(0));
				String fName = l.get(1);
				String lName = l.get(2);
				String password = l.get(3);
				String email = l.get(4);
				Faculty newF = new Faculty(id, fName, lName, password, email);
				faculty.add(newF);			
			}
			
		} catch (DLException e) {
			e.printStackTrace();
		}
		return faculty;
	}
	
	private static ArrayList<Papers> populatePapers(MySQLDatabase sql) {
		
		ArrayList<Papers> research = new ArrayList<>();
		
		try {
			sql.connect();
			
			String stmnt = "SELECT * FROM papers";
			
			ArrayList<ArrayList> resultsTable = sql.getData(stmnt);
			
			for(ArrayList<String> l: resultsTable) {
				int id = Integer.parseInt(l.get(0));
				String title = l.get(1);
				String pAbstract = l.get(2);
				String citation = l.get(3);
				Papers paper = new Papers(id, title, pAbstract, citation);
				research.add(paper);
			}
			
		} catch (DLException e) {
			e.printStackTrace();
		}
		return research;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private static void papersToAuthors(MySQLDatabase sql) {
		try {	
			for(Faculty f: faculty) {
				ArrayList<Papers> authored = new ArrayList<>();
				sql.connect();
				
				String stmnt = "SELECT papers.id FROM papers JOIN authorship ON "
						+ "papers.id = paperid JOIN faculty ON facultyid = "
						+ "faculty.id WHERE faculty.id = "+f.getId();
				
				ArrayList<ArrayList> paperList = sql.getData(stmnt);
				for(ArrayList<String> s: paperList) {
					for(Papers p: research) {
						if(Integer.parseInt(s.get(0)) == p.getId()) {
							authored.add(p);
							break;
						}
					}
				}
				f.setPapers(authored);
			}
		} catch (DLException e) {
			e.printStackTrace();
		}
	}

}//END SQLMain
