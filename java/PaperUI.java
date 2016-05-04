import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Christopher Caulfield
 */

public class PaperUI implements ActionListener{
   
   private JFrame frame;
   private JPanel searchPanel;
   private JLabel  searchInfo;
   private JTextField searchBar;
   private JComboBox quickSearch;
   private JButton searchButton;
   private JPanel tablePanel;
   private String dataValues[][];
   private JTable table;
   private JScrollPane scrollPane;
   private JPanel infoPanel;
   private JPanel editPanel;
   private JButton deleteButton;
   private JButton editButton;
   private JTextArea textArea;
   private JScrollPane scrollArea;
   private JTextField titleField;
   private JTextField firstNameField;
   private JTextField lastNameField;
   private JTextField keywordsField;
   private JTextField citationField;
   private JTextArea authorTextArea;
   private JScrollPane authorScrollArea;
   
      
   //determine user permissions
   private boolean hasAccess;
   private String facultyEmail;
   
   
   
   public PaperUI(){
      //
      //create frame
      frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());
      frame.setLocationRelativeTo( null );
      this.createGUI();
      frame.setSize(800, 550);
      frame.setVisible(true);
   }
   
   public PaperUI(String facultyEmail){
      //setup preferences
      hasAccess = true;
      this.facultyEmail = facultyEmail;
      //create frame
      frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());
      frame.setLocationRelativeTo( null ); 
      this.createGUI();
      frame.setSize(700, 450);
      frame.setVisible(true);
   }
   
   
   public void createGUI(){
   
      //create search panel
      searchPanel = new JPanel();
      searchPanel.setLayout(new FlowLayout());

      //search bar
      searchInfo = new JLabel("Enter Information: ", JLabel.RIGHT);
      searchBar = new JTextField(15);
      
      //quick search bombo box
      String[] searchWords = { "Title", "Keywords", "Abstract","First Name","Last Name","Citation"};
      quickSearch = new JComboBox(searchWords);
      
      //search button
      searchButton = new JButton("Search");
      
      
      searchPanel.add(searchBar);
      searchPanel.add(quickSearch);
      searchPanel.add(searchButton);
      
      //action listener
      searchButton.addActionListener(this);
      
      frame.add(searchPanel, BorderLayout.NORTH);
      
      tablePanel = new JPanel();
      tablePanel.setLayout( new BorderLayout());
      
      //Create columns names
		String columnNames[] = { "Paper title", "First name", "Last name","Citation","Abstract","Email"};

		// Create some data
		String dataValues[][] =
		{
			{ "12", "234", "67","12", "234", "67" },
			{ "-123", "43", "853","12", "234", "67" },
			{ "93", "89.2", "109","12", "234", "67" },
			{ "279", "9033", "3092","12", "234", "67" }
		};

		// Create a new table instance
		table = new JTable( dataValues, columnNames );

		// Add the table to a scrolling pane
		scrollPane = new JScrollPane( table );
		tablePanel.add( scrollPane, BorderLayout.CENTER );

      frame.add(tablePanel, BorderLayout.CENTER);
            
      //fill information panel 
      infoPanel = new JPanel();
      infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));      
      
      //title
      infoPanel.add(new JLabel("Title: ", JLabel.RIGHT));
      titleField = new JTextField();
      infoPanel.add(titleField);
     
     //Author
      authorTextArea = new JTextArea(20,25);
      authorScrollArea = new JScrollPane(authorTextArea);
      
      infoPanel.add(new JLabel("Author: ", JLabel.RIGHT));
      infoPanel.add(authorScrollArea);
      
      //keywords
      infoPanel.add(new JLabel("Keywords: ", JLabel.RIGHT));
      keywordsField = new JTextField();
      infoPanel.add(keywordsField);
      
      
      //Citation 
      infoPanel.add(new JLabel("Citation: ", JLabel.RIGHT));
      citationField = new JTextField();
      infoPanel.add(citationField); 
      
      //Abstract
      textArea = new JTextArea(20,25);
      scrollArea = new JScrollPane(textArea);
      
      infoPanel.add(new JLabel("Abstract: ", JLabel.RIGHT));
      infoPanel.add(scrollArea);
      
      frame.add(infoPanel, BorderLayout.EAST);
      
      //create edit button panel
      editPanel = new JPanel();
      editPanel.setLayout(new FlowLayout());
      
      if(hasAccess == true){
         deleteButton = new JButton("Delete");
         editPanel.add(deleteButton);
         deleteButton.addActionListener(this);
         
         editButton = new JButton("Insert/Update");
         editPanel.add(editButton);
         editButton.addActionListener(this);
      }
      else{
         titleField.setEditable(false);
         firstNameField.setEditable(false);
         lastNameField.setEditable(false);
         keywordsField.setEditable(false);
         citationField.setEditable(false);
         textArea.setEditable(false);
      }
     
      frame.add(editPanel, BorderLayout.SOUTH);
   }
   
   
   //actionPerformed
   public void actionPerformed(ActionEvent ae){      
      if(ae.getActionCommand() == "Delete"){                    // Wait for someone to push Go!
         System.out.println("Delete selected");
      }
      else if(ae.getActionCommand() == "Insert/Update"){
         System.out.println("Insert/Update selected");
      }  
      else if(ae.getActionCommand() == "Search"){
         System.out.println("Search selected");
 
      }    
   }// end of actionPerformed

   
   

}







