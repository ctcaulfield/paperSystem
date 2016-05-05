/**
 * @author Ian Kitchen
 * @author Christopher Caulfield 
 * @author Andrew Cerkanowicz
 * @author Michael Schirmer
 * 
 * This is the main class for opening and closing the MySQL Database as well as
 * running the methods that add and modify the data in the database
 */
public class SQLMain {

	public static void main(String[] args) {
		
		/************************* Start **************************/
		
		 MySQLUI signIn = new MySQLUI();
         signIn.setVisible( true );
         signIn.setSize(300, 300);
         signIn.setLocationRelativeTo( null );
         signIn.setDefaultCloseOperation( javax.swing.JFrame.EXIT_ON_CLOSE );
         signIn.pack(); 
			
	}//END main

}//END SQLMain
