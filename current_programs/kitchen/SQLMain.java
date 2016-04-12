/**
 * @author Ian Kitchen
 * 
 * This is the main class for opening and closing the MySQL Database as well as
 * running the methods that add and modify the data in the database
 */
public class SQLMain {

	public static void main(String[] args) {
		
		/************************* Start **************************/
		
		MySQLDatabase msqldb = new MySQLDatabase();
		Equipment equip1 = new Equipment(1, "F17", "Fighter Jet", 2);
		Equipment equip2 = new Equipment(2, "B26", "Bomber", 6);
		boolean isStmnt = false;
		
		try {
			msqldb.connect();
			equip1.post();
			equip2.post();
			//b.
            isStmnt = equip1.fetch();
			if(isStmnt) {
				System.out.println("ID: " + equip1.getID() + "\n" +
						           "Name: " + equip1.getName() + "\n" +
						           "Description: " + equip1.getDescription() + 
						           "\nCapacity: " + equip1.getCapacity());
			}
			else
				System.out.println("Fetch Failed");
			
			//c.
			equip1.swap(2);
			
			//d.
			isStmnt = equip1.fetch();
			if(isStmnt) {
				System.out.println("ID: " + equip1.getID() + "\n" +
						           "Name: " + equip1.getName() + "\n" +
						           "Description: " + equip1.getDescription() + 
						           "\nCapacity: " + equip1.getCapacity());
			}
			else
				System.out.println("Fetch Failed");
			
			//e.
			Equipment equip3 = new Equipment(2);
			isStmnt = equip3.fetch();
			if(isStmnt) {
				System.out.println("ID: " + equip3.getID() + "\n" +
						           "Name: " + equip3.getName() + "\n" +
						           "Description: " + equip3.getDescription() + 
						           "\nCapacity: " + equip3.getCapacity());
			}
			else
				System.out.println("Fetch Failed");
			
			equip1.delete();
			equip2.delete();
			equip3.delete();
			
			msqldb.close();
		} catch (DLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}//END main

}//END SQLMain
