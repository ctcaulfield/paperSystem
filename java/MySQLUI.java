import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.math.BigInteger;
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
   private JButton jbGo;
   private ButtonGroup bgUsers;
   private String[] theArgs;
   
   private PaperUI paperView;
   
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
	   faculty = populateFaculty(msqldb);
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

      //remove this when finished testing
      jtfEmail = new JTextField("sjz@it.rit.edu");             // Add the TextField
      jtfPass = new JTextField("sjz");
        
      //jtfEmail = new JTextField("Enter email...");             // Add the TextField
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
   
   
   public void actionPerformed(ActionEvent ae){      
      if(ae.getActionCommand() == "Student/Guest"){                    // Wait for someone to push Go!
         System.out.println("Log in button clicked");                       
            System.out.println("Open Student/Guest view");
            paperView = new PaperUI();
            this.dispose();
            
      }
      else if(ae.getActionCommand() == "Log in"){
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
        	 
        	 // we need to zero pad the hash to get the full 32 chars.
        	 while(hashtext.length() < 32 ){
        	     hashtext = "0"+hashtext;
        	 }  
		 } catch (NoSuchAlgorithmException e) {
			System.out.println("Encoding failed");
		 }
        	 
         for(Faculty f: faculty) {
             if(f.getEmail().equals(username) && f.getPassword().equals(hashtext)){
                 System.out.println("Open Faculty/Admin view"); 
                 paperView = new PaperUI(f.getEmail());
                 this.dispose();
             }
		 }
 
      }     
   }// end of actionPerformed
   
   //connects to database and populates faculty information 
   @SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList<Faculty> populateFaculty(MySQLDatabase sql) {
		
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
