import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * @author Christopher Caulfield
 * @author Ian Kitchen
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
   private JTextArea textArea;
   private JScrollPane scrollArea;
   
   //determine user permissions
   private boolean hasAccess;
   private String facultyEmail;
   
   //data layer
   private MySQLDatabase msqldb;
   
   //data structures
   private ArrayList<Faculty> faculty = new ArrayList<>();
   private ArrayList<Papers> research = new ArrayList<>();
		   
   public PaperUI(ArrayList<Faculty> faculty){
	  //instantiate the data layer
      msqldb = new MySQLDatabase();
      
      //populate data structures
      this.faculty = faculty;
      research = populatePapers();
      papersToAuthors();
      populateKeywords();
	   
      //create frame
      frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());
      frame.setLocationRelativeTo( null );
      this.createGUI();
      frame.setSize(800, 550);
      frame.setVisible(true);
   }
   
   public PaperUI(String facultyEmail, ArrayList<Faculty> faculty){
      //setup preferences
      hasAccess = true;
      this.facultyEmail = facultyEmail;
      
      //instantiate the data layer
      msqldb = new MySQLDatabase();
      
      //populate data structures
      this.faculty = faculty;
      research = populatePapers();
      papersToAuthors();
      populateKeywords();
      
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
      infoPanel.add(new JTextField()); 
      
      textArea = new JTextArea(20,15);
      scrollArea = new JScrollPane(textArea);
      //Abstract
      infoPanel.add(new JLabel("Abstract: ", JLabel.RIGHT));
      infoPanel.add(scrollArea);
      
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
   
    /**
     * Loads the papers data structure with the results from the database
	 * 
	 * @return a list of paper objects
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList<Papers> populatePapers() {
		
		ArrayList<Papers> research = new ArrayList<>();
		
		try {
			msqldb.connect();
			
			//create an SQL statement and execute it
			String stmnt = "SELECT * FROM papers";
			ArrayList<ArrayList> resultsTable = msqldb.getData(stmnt);
			
			//for each row of the data returned, create a paper object and store
			//it in an array
			for(ArrayList<String> l: resultsTable) {
				int id = Integer.parseInt(l.get(0));
				String title = l.get(1);
				String pAbstract = l.get(2);
				String citation = l.get(3);
				Papers paper = new Papers(id, title, pAbstract, citation);
				research.add(paper);
			}
			msqldb.close();
			
		} catch (DLException e) {
			e.printStackTrace();
		}
		return research;
	}//END populate papers
	
	/**
	 * Associates papers to authors and vice versa based on which author/s
	 * participate in writing a specific paper. 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private void papersToAuthors() {
		try {
			//FOR each faculty object
			for(Faculty facultyObj: faculty) {
				//list of papers for this faculty object
				ArrayList<Papers> authored = new ArrayList<>();
				msqldb.connect();
				
				//create the SQL statement and execute it
				String stmnt = "SELECT papers.id FROM papers JOIN authorship ON "
						+ "papers.id = paperid JOIN faculty ON facultyid = "
						+ "faculty.id WHERE faculty.id = "+facultyObj.getId();
				ArrayList<ArrayList> paperList = msqldb.getData(stmnt);
				
				//for each row in the data set returned
				for(ArrayList<String> dataSet: paperList) {
					//for each paper object
					for(Papers paperObj: research) {
						//IF the paperID in the data set is equal to the current
						//paper object's ID, then the current faculty object is
						//the author of the paper
						if(Integer.parseInt(dataSet.get(0)) == paperObj.getId()) {
							authored.add(paperObj);
							
							//add author to the paper object's authors array
							paperObj.addAuthor(facultyObj);
							break;
						}
					}
				}
				//set the current the papers authored attribute of the faculty 
				//object
				facultyObj.setPapers(authored);
				msqldb.close();
			}
		} catch (DLException e) {
			e.printStackTrace();
		}
	}//END papersToAuthors
	
	/**
	 * 
	 * @param sql
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void populateKeywords() {
		try {
			msqldb.connect();
			for(Papers paperObj: research) {
				ArrayList<String> newKeywords = new ArrayList<>();
				
				//create the sql statement and execute it
				String stmnt = "SELECT keyword FROM paper_keywords JOIN papers"
						+ " ON paper_keywords.id = papers.id WHERE papers.id "
						+ "= "+paperObj.getId();
				ArrayList<ArrayList> dataSet = msqldb.getData(stmnt);
				
				//FOR each row in the data set
				for(ArrayList<String> keywords: dataSet) {
					//get the keywords and add them to a new keyword list
					for(String s: keywords) {
						newKeywords.add(s);
					}
				}
				//add the list of keywords to the paper object
				paperObj.setKeywords(newKeywords);
			}
		} catch (DLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//END populateKeywords
   
}//END PaperUI







