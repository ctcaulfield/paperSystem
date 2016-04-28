import java.util.ArrayList;

/**
 * @author Ian Kitchen
 * 
 * This is the main class for opening and closing the MySQL Database as well as
 * running the methods that add and modify the data in the database
 */
public class SQLMain {
	
	private static MySQLDatabase msqldb;

	public static void main(String[] args) {
		
		/************************* Start **************************/
		
		msqldb = new MySQLDatabase();
		ArrayList<Faculty> faculty = populateFaculty(msqldb);
		int count = 1;
		for(Faculty f: faculty) {
			System.out.println("Faculty number " + count);
			System.out.println("ID: " + f.getId());
			System.out.println("Name: "+f.getFirstName()+" "+f.getLastName());
			System.out.println("Password: "+f.getPassword());
			System.out.println("Email: "+f.getEmail()+"\n");
		}
			
	}//END main
	
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

}//END SQLMain
