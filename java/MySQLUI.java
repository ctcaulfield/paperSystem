import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.math.BigInteger;
import java.security.*;

/**
 * @author Christopher Caulfield
 * @author Ian Kitchen
 * 
 * This class opens up the login screen and authenticates the user if 
 * attempting to log in
 */
public class MySQLUI extends JFrame implements ActionListener{


   private MySQLDatabase msqldb; //Data layer class that interacts with the db
   private ArrayList<Faculty> faculty; //Array of faculty objects
   private JPanel jpContainer; // The JPanel that holds the Messages panel.
   
   private JPanel jpMessages;  
   private JPanel jpWelcome;
   private JPanel jpSpace; 
   private JPanel jpWho;
   private JLabel jlWelcome;
   private JLabel jlWho;
   private JTextField jtfEmail;
   private JTextField jtfPass;
   private JButton jbLogIn;
   private JButton jbStudentGuest;
   
   private PaperUI paperView; // The view that displays the paper info
   
   /**
    * The font that is used throughout the login screen.
    */
   Font font = new Font("Helvetica", Font.BOLD, 12);

   /**
    * Creates the required JPanels for the Login screen.
    * It then sets up the log in screen, and adds
    * action listeners onto the buttons.
    */
   public MySQLUI(){  
	   //instantiate the data layer
	   msqldb = new MySQLDatabase();
	   faculty = populateFaculty(msqldb);
	   
	   //Create new JPanel to hold many JPanels that will wait to 
	   //collect information
	   jpContainer = new JPanel();
	   add( jpContainer );
	 
	   //Create a JPanel to hold many rows of information
	   jpMessages = new JPanel(new GridLayout(0, 1, 0, 10));   
	   jpContainer.add( jpMessages, BorderLayout.CENTER );
	
	   //Adds a space in the grid
	   jpSpace = new JPanel();                                 
	   jpMessages.add( jpSpace );
	   jpWelcome = new JPanel();
	         
	   //Add JLabels
	   jlWelcome = new JLabel("Welcome!");                      
	   jlWelcome.setFont( font );
	   jpWelcome.add( jlWelcome );
	   jpMessages.add( jpWelcome );
	 
	   // Add Label "Who are you"
	   jpWho = new JPanel();
	   jlWho = new JLabel("Please log in");                   
	   jlWho.setFont( font );
	   jpWho.add( jlWho );
	    
	   jpMessages.add( jpWho );
	
	   //remove this when finished testing
	   jtfEmail = new JTextField("jdmics@rit.edu");             // Add the TextField
	   jtfPass = new JTextField("sjz");
	    
	   // Add the TextField
	   //jtfEmail = new JTextField("Enter email...");             
	   //jtfPass = new JTextField("Enter password...");
	 
	   jpMessages.add( jtfEmail );
	   jpMessages.add( jtfPass ); 
	 
	   jbStudentGuest = new JButton("Student/Guest");
	 
	   jpMessages.add( jbStudentGuest );
	 
	   jbStudentGuest.addActionListener(this);
	
	   jbLogIn = new JButton("Log in");
	 
	   jpMessages.add( jbLogIn );
	 
	   jbLogIn.addActionListener(this);
      
   }// end of TCSplash Constructor
   
   /**
    * Listeners for login
    */
   public void actionPerformed(ActionEvent ae){  
	   
       boolean isUser = false;
	   
       //if the student/guest button was clicked
      if(ae.getActionCommand().equalsIgnoreCase("Student/Guest")){
    	  // print to console that the login button was pressed as well as the
    	  // Student/guest button 
          System.out.println("Log in button clicked");
          System.out.println("Open Student/Guest view");
          
          //open an instance of the Papers UI for students/guests
          paperView = new PaperUI(faculty);
          this.dispose();  
      }
      //IF the Log in button was pressed
      else if(ae.getActionCommand().equalsIgnoreCase("Log in")){
         System.out.println("Student/Guest button clicked"); 
         String hashtext = "";
         String username = jtfEmail.getText();
         String password = jtfPass.getText(); 
    	 MessageDigest m;
    	 
		 try {
		     //encode the password given by user
		     m = MessageDigest.getInstance("MD5");
			 m.reset();
        	 m.update(password.getBytes());
        	 byte[] digest = m.digest();
        	 BigInteger bigInt = new BigInteger(1,digest);
        	 hashtext = bigInt.toString(16);
        	 
        	 // zero pad the hash to get the full 32 characters
        	 while(hashtext.length() < 32 ){
        	     hashtext = "0"+hashtext;
        	 }  
		 } catch (NoSuchAlgorithmException e) {
			System.out.println("Encoding failed");
		 }
        	 
         for(Faculty f: faculty) {
             if(f.getEmail().equals(username) && f.getPassword().equals(hashtext)){
                 System.out.println("Open Faculty/Admin view"); 
                 paperView = new PaperUI(f.getEmail(), faculty);
                 this.dispose();
                 isUser = true;
             }
		 }
         //IF the username or password was not found
         if(!isUser)
         {
        	 //display to user that a wrong password was entered
        	 JOptionPane.showMessageDialog(null, "Wrong username or password", 
        			 "", JOptionPane.ERROR_MESSAGE);
         }
 
      }     
   }// end of actionPerformed
   
   /**
    * Connects to the database and populates faculty information 
    * 
    * @param sql - the data layer class
    * @return a list of the faculty returned from the database
    */
   @SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList<Faculty> populateFaculty(MySQLDatabase sql) {
		
		ArrayList<Faculty> faculty = new ArrayList<>();
		
		try {
			//connect to db
			sql.connect();
			
			//create SQL statement
			String stmnt = "SELECT * FROM faculty";
			
			//get results of the query
			ArrayList<ArrayList> resultsTable = sql.getData(stmnt);
			
			//FOR each of the results
			for(ArrayList<String> l: resultsTable) {
				// get the faculty ID, first name, last name, password, and 
				// email and create a new faculty object with the results
				int id = Integer.parseInt(l.get(0));
				String fName = l.get(1);
				String lName = l.get(2);
				String password = l.get(3);
				String email = l.get(4);
				Faculty newF = new Faculty(id, fName, lName, password, email);
				
				//add new faculty object to an array of faculty
				faculty.add(newF);			
			}
			
		} catch (DLException e) {
			e.printStackTrace();
		}
		return faculty;
	}//END populateFaculty
	
}// end of MySQLUI
