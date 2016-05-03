import java.awt.*;
import javax.swing.*;

/**
 * @author Christopher Caulfield
 */

public class PaperUI{
   
   private JFrame frame;
   private JPanel searchPanel;
   private JLabel  searchInfo;
   private JTextField searchBar;
   private JComboBox quickSearch;
   private JButton loginButton;
   private JPanel tablePanel;
   private String dataValues[][];
   private JTable table;
   private JScrollPane scrollPane;
   private JPanel infoPanel;
   private JPanel editPanel;
   private JButton deleteButton;
   private JButton editButton;
   
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
      frame.setSize(700, 450);
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
      loginButton = new JButton("Search");
      
      
      searchPanel.add(searchBar);
      searchPanel.add(quickSearch);
      searchPanel.add(loginButton);
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
      infoPanel.add(new JTextField());
      
      //first name
      infoPanel.add(new JLabel("First Name: ", JLabel.RIGHT));
      infoPanel.add(new JTextField());
     
      //last name 
      infoPanel.add(new JLabel("Last Name: ", JLabel.RIGHT));
      infoPanel.add(new JTextField()); 
     
      //keywords name 
      infoPanel.add(new JLabel("Keywords: ", JLabel.RIGHT));
      infoPanel.add(new JTextField()); 
      
      //Citation 
      infoPanel.add(new JLabel("Citation: ", JLabel.RIGHT));
      infoPanel.add(new JTextField(15)); 
      
      //Abstract
      infoPanel.add(new JLabel("Abstract: ", JLabel.RIGHT));
      infoPanel.add(new JTextArea(70, 20));
      
      frame.add(infoPanel, BorderLayout.EAST);
      
      //create edit button panel
      editPanel = new JPanel();
      editPanel.setLayout(new FlowLayout());
      
      deleteButton = new JButton("Delete");
      editPanel.add(deleteButton);
      
      editButton = new JButton("Insert/Update");
      editPanel.add(editButton);
      
      frame.add(editPanel, BorderLayout.SOUTH);
   }
   
   

}







