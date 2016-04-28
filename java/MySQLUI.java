import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.security.*;
/**
 * @author Christopher Caulfield
 */
public class MySQLUI extends JFrame implements ActionListener{


	private static MySQLDatabase msqldb;
	private static ArrayList<Faculty> faculty;

   /**
    * The JPanel that holds the Messages panel.
    */
   private JPanel jpContainer;
   
   //The JPanel that holds all of the panels other than jpContainer.
   private JPanel jpMessages;  
   private JPanel jpWelcome;
   private JPanel jpSpace; 
   private JPanel jpWho;
   private JLabel jlWelcome;
   private JLabel jlWho;
   private JTextField jtfUser;
   private JTextField jtfPass;
   private JButton jbLogIn;
   private JButton jbStudentGuest;
   private JButton jbGo;
   private ButtonGroup bgUsers;
   private String[] theArgs;
   
   /**
    * The font that is used throughout the splash screen.
    */
   Font font = new Font("Helvetica", Font.BOLD, 12);
   
   /**
    * On start, calls the MySQLUI constructor. Creates
    * a MySQLUI JFrame. It then sets it to visible.
    * @param args Unused
    */
   public static void main(String [] args){
      msqldb = new MySQLDatabase();
		faculty = populateFaculty(msqldb);


      MySQLUI signIn = new MySQLUI();
         signIn.setVisible( true );
         signIn.setSize(300, 300);
         signIn.setLocationRelativeTo( null );
         signIn.setDefaultCloseOperation( EXIT_ON_CLOSE );
         signIn.pack();    
         
         
   }// end of main

   /**
    * Creates the required JPanels for the TCSplash.
    * It then sets up the splash screen, and adds
    * action listeners onto the buttons.
    */
   public MySQLUI(){    
      jpContainer = new JPanel();                              //Create new JPanel to hold many JPanels that will wait to collect information
      add( jpContainer );
     
      jpMessages = new JPanel(new GridLayout(0, 1, 0, 10));    //Create a JPanel to hold many rows of information
      jpContainer.add( jpMessages, BorderLayout.CENTER );
    
      jpSpace = new JPanel();                                  //Adds a space in the grid
      jpMessages.add( jpSpace );
     
      jpWelcome = new JPanel();
             
      jlWelcome = new JLabel("Welcome!");                      //Add JLabels
      jlWelcome.setFont( font );
      jpWelcome.add( jlWelcome );
        
      jpMessages.add( jpWelcome );
     
      jpWho = new JPanel();
       
      jlWho = new JLabel("Please log in");                      // Add Label "Who are you"
      jlWho.setFont( font );
      jpWho.add( jlWho );
        
      jpMessages.add( jpWho );
        
      jtfUser = new JTextField("Enter username...");             // Add the TextField
      jtfPass = new JTextField("Enter password...");
     
      jpMessages.add( jtfUser );
      jpMessages.add( jtfPass ); 
     
    
      jbStudentGuest = new JButton("Student/Guest");
     
      jpMessages.add( jbStudentGuest );
     
      jbStudentGuest.addActionListener(this);


      jbLogIn = new JButton("Log in");
     
      jpMessages.add( jbLogIn );
     
      jbLogIn.addActionListener(this);
      
   }// end of TCSplash Constructor
   
   
   public void actionPerformed(ActionEvent ae){      
      if(ae.getActionCommand() == "Student/Guest"){                    // Wait for someone to push Go!
         System.out.println("Log in button clicked");                       
            System.out.println("Open Student/Guest view");
            this.dispose();
      }
      else if(ae.getActionCommand() == "Log in"){
         System.out.println("Student/Guest button clicked"); 
         String username = jtfUser.getText();
         String password = jtfPass.getText();
         try {  
            byte[]passbyte = password.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[]digest = md.digest(passbyte);
            password = new String(digest,"UTF-8");
         } 
         catch (Exception e) {
            System.out.println("encoding failed");
         }   
         for(Faculty f: faculty) {
           if(f.getFirstName() == username && f.getPassword() == password){
             System.out.println("Open Faculty/Admin view"); 
             this.dispose();
           }
		   }
         //if survives through loop - nothing was found 
         System.out.println("Password or username incorrect");  
      }     
   }// end of actionPerformed
   
   //connects to database and populates faculty information 
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
	
}// end of MySQLUI
