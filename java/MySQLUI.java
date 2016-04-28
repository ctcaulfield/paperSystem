import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Christopher Caulfield
 */
public class MySQLUI extends JFrame implements ActionListener{
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
   private JTextField jtfIP;
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
      jtfIP = new JTextField("Enter password...");
     
      jpMessages.add( jtfUser );
      jpMessages.add( jtfIP ); 
     
    
      jbStudentGuest = new JButton("Student/Guest");
     
      jpMessages.add( jbStudentGuest );
     
      jbStudentGuest.addActionListener(this);


      jbLogIn = new JButton("Log in");
     
      jpMessages.add( jbLogIn );
     
      jbLogIn.addActionListener(this);
      
   }// end of TCSplash Constructor
   
   
   public void actionPerformed(ActionEvent ae){      
      if(ae.getActionCommand() == "Log in"){                    // Wait for someone to push Go!
         System.out.println("Log in button clicked");             
         
         //if user name/password field is filled - continue
         //then check if the user name/password is true
            
            System.out.println("Open Faculty/Admin view");
            //teacher = new TeacherView2(jtfUser.getText());
            this.dispose();
      }
      else if(ae.getActionCommand() == "Student/Guest"){
         System.out.println("Student/Guest button clicked"); 
         System.out.println("Open Student/Guest view");
         //teacher = new TeacherView2(jtfUser.getText());
         this.dispose();
      }     
   }// end of actionPerformed
}// end of MySQLUI
