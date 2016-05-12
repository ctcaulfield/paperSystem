import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author Christopher Caulfield
 * @author Ian Kitchen
 */

public class PaperUI extends JFrame implements ActionListener,MouseListener{
   
	private final String TITLE = "Title";
	private final String KEY = "Keywords";
	private final String ABS = "Abstract";
	private final String FN = "First Name";
	private final String LN = "Last Name";
	private final String CIT = "Citation";
	
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
   private JButton updateButton;
   private JButton deleteButton;
   private JButton insertButton;
   private JButton clearButton;
   private JTextArea textArea;
   private JScrollPane scrollArea;
   private JTextField titleField;
   private JTextField emailField;
   private JTextField keywordsField;
   private JTextField citationField;
   private JTextArea authorTextArea;
   private JScrollPane authorScrollArea;
   private DefaultTableModel model;
   private JTextArea abstractTextArea;
   private JScrollPane abstractScrollArea;
   
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
      model = new DefaultTableModel(){
    	  //make it so the cells are not editable
    	  @Override
  	      public boolean isCellEditable(int row, int column) {
  	         return false;
  	      }
      };
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
      //make it so the cells are not editable
      model = new DefaultTableModel(){
    	  @Override
    	  public boolean isCellEditable(int row, int column) {
    	     //all cells false
    	     return false;
    	  }
      };
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
      
      //quick search combo box
      String[] searchWords = { TITLE, KEY, ABS,FN,LN,CIT};
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
		// Create a new table instance
		table = new JTable();
        model.setColumnIdentifiers(columnNames);
		table.setModel(model);
      table.addMouseListener(this);
      
      
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
 
      //email
      infoPanel.add(new JLabel("Email: ", JLabel.RIGHT));
      emailField = new JTextField();
      infoPanel.add(emailField);     
          
      //keywords name 
      infoPanel.add(new JLabel("Keywords: ", JLabel.RIGHT));
      keywordsField = new JTextField();
      infoPanel.add(keywordsField);
      
      //Citation 
      infoPanel.add(new JLabel("Citation: ", JLabel.RIGHT));
      citationField = new JTextField();
      infoPanel.add(citationField); 
      
      textArea = new JTextArea(20,25);
      scrollArea = new JScrollPane(textArea);
      
      //Abstract
      abstractTextArea = new JTextArea(20,25);
      abstractScrollArea = new JScrollPane(abstractTextArea);
      
      infoPanel.add(new JLabel("Abstract: ", JLabel.RIGHT));
      infoPanel.add(abstractScrollArea);
      
      frame.add(infoPanel, BorderLayout.EAST);
      
      //create edit button panel
      editPanel = new JPanel();
      editPanel.setLayout(new FlowLayout());
      clearButton = new JButton("Clear Fields");
      editPanel.add(clearButton);
      clearButton.addActionListener(this);
      
      if(hasAccess == true){
         deleteButton = new JButton("Delete");
         editPanel.add(deleteButton);
         deleteButton.addActionListener(this);
         
         insertButton = new JButton("Insert");
         editPanel.add(insertButton);
         insertButton.addActionListener(this);
         
         updateButton = new JButton("Update");
         editPanel.add(updateButton);
         updateButton.addActionListener(this);
      }
      else{
         titleField.setEditable(false);
         authorTextArea.setEditable(false);
         keywordsField.setEditable(false);
         citationField.setEditable(false);
         textArea.setEditable(false);
      }
      
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
	 * Grabs a list of keywords associated with the paper objects
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
   
   //actionPerformed
   public void actionPerformed(ActionEvent ae){      
      if(ae.getActionCommand() == "Delete"){  
                        // Wait for someone to push Go!
         System.out.println("Delete selected");
      }
      else if(ae.getActionCommand().equalsIgnoreCase("Insert")){
         System.out.println("Insert");
         
      }  
       else if(ae.getActionCommand().equalsIgnoreCase("Update")){
         System.out.println("Update selected");
         //update faculty

          for(Papers p: research) {
              //values.get(0) is the title name from arrayList
              String title = p.getTitle().trim();
              if(title.equals(titleField.getText())){                 
                 ArrayList<Faculty> authors = p.getAuthors();
                 //ArrayList<Fac
                 p.setTitle(titleField.getText());
                 p.setAbstract(abstractTextArea.getText());
                 //p.setAuthors(ArrayList<Faculty> authors);
              }
          }//end of Papers for loop
      }     
      else if(ae.getActionCommand().equalsIgnoreCase("Clear fields")){
          titleField.setText("");
          emailField.setText("");
          keywordsField.setText("");
          citationField.setText("");
          authorTextArea.setText("");
          abstractTextArea.setText("");      
      }   
      else if(ae.getActionCommand().equalsIgnoreCase("Search")){
      	model.setRowCount(0);
         System.out.println("Search selected");
         String searchString = searchBar.getText();
         String searchCriteria = quickSearch.getSelectedItem().toString();
         
         for(Papers p: research) {
        	 String[] rowValues = new String[6];
        	 ArrayList<Faculty> authors = p.getAuthors();
        	 //IF the search criteria is by title
        	 if(searchCriteria.equalsIgnoreCase(TITLE)) {
        		 //IF the search word/s are in a title
        		 if(p.getTitle().toLowerCase().contains(searchString.toLowerCase())) {
        			 rowValues = setRow(p, authors);
        		 }
        	 }
        	 //IF the search criteria is by keywords
        	 else if(searchCriteria.equalsIgnoreCase(KEY)) {
        		 ArrayList<String> keywords = p.getKeywords();
        		 for(String s: keywords) {
        			 if(s.toLowerCase().contains(searchString.toLowerCase())) {
        				 rowValues = setRow(p, authors);
        				 break;
        			 }
        		 }
        	 }
        	 //IF the search criteria is by abstracts
        	 else if(searchCriteria.equalsIgnoreCase(ABS)) {
        		 if(p.getAbstract().toLowerCase().contains(searchString.toLowerCase())) {
        			 rowValues = setRow(p, authors);
        		 }
        	 }
        	 //IF the search criteria is by first names
        	 else if(searchCriteria.equalsIgnoreCase(FN)) {
        		 for(Faculty f: authors) {
        			 if(f.getFirstName().toLowerCase().contains(searchString.toLowerCase())) {
        				 rowValues = setRow(p, authors);
        				 break;
        			 }
        		 }
        	 }
        	 //IF the search criteria is by last names
        	 else if(searchCriteria.equalsIgnoreCase(LN)) {
        		 for(Faculty f: authors) {
        			 if(f.getLastName().toLowerCase().contains(searchString.toLowerCase())) {
        				 rowValues = setRow(p, authors);
        				 break;
        			 }
        		 }
        	 }
        	 //IF the search criteria is by the citations
        	 else if(searchCriteria.equalsIgnoreCase(CIT)) {
        		 if(p.getCitation().toLowerCase().contains(searchString.toLowerCase())) {
        			 rowValues = setRow(p, authors);
        		 }
        	 }
        	 //ELSE no match exists: currently wrong
        	 else
        		 JOptionPane.showMessageDialog(null, "No results matched your query", 
            			 "", JOptionPane.ERROR_MESSAGE);
        	 
        	 //add the new row 
        	 try {
        	     rowValues[0].equals("");
        	     model.addRow(rowValues);
        	 } catch(NullPointerException npe) {
        		 System.out.println("No data, skip this line.");
        	 }
 		 }
         //model.removeRow(0);
         System.out.println("Search selected");  
      }    

   }// end of actionPerformed
     
   /**
    * 
    * @param p
    * @param authorList
    * @return
    */
   private String[] setRow(Papers p, ArrayList<Faculty> authorList) {
	   String[] rowValues = new String[6];
	   ArrayList<Faculty> authors = authorList;
	   String fName = "";
	   String lName = "";
	   String email = "";
	   for(Faculty f: authors) {
	       fName += f.getFirstName() + " ";
	       lName += f.getLastName() +  " ";
	       email += f.getEmail() + " ";
	   }
	   for(int j=0; j<table.getColumnCount(); j++) {
	       switch(j) {
		       case 0: rowValues[j] = p.getTitle();
			 		   break;
			 	
			   case 1: rowValues[j] = fName;
			 		   break;
			 	
			   case 2: rowValues[j] = lName;
			 		   break;
			 	
			   case 3: rowValues[j] = p.getAbstract();
			 		   break;
			 	
			   case 4: rowValues[j] = p.getCitation();
			 		   break;
			 	
			   case 5: rowValues[j] = email;
			 }
	   }
	   return rowValues;
   }
   
  //Mouse listeners - all methods must be implemented or else it will throw an error 
  public void mousePressed(MouseEvent e){
     int row = table.rowAtPoint(e.getPoint());
     int i=0;
     ArrayList<String> values = new ArrayList<String>();
     while(i<table.getColumnCount()){
       //may have to change this logic if
       //does not convert to String safely
       String value = (table.getModel().getValueAt(row,i))+" ";
       values.add(value);
       i++;
     }
     for(Papers p: research) {
        //values.get(0) is the title name from arrayList
        String title = p.getTitle().trim();
        String selected = values.get(0).trim();
        if(title.equals(selected)){
           System.out.println("made it");
           titleField.setText(p.getTitle());
           ArrayList<String> keywords = p.getKeywords();
           String key = "";
           for(String keyword:keywords){
             key+=(keyword+",");
           }
           //remove last comma from key
           key = key.substring(0, key.length() - 1);
           keywordsField.setText(key);
           citationField.setText(p.getCitation());
           
           //get faculty data
           ArrayList<Faculty> authors = p.getAuthors();
           ArrayList<String> flnames = new ArrayList();
           ArrayList<String> emails = new ArrayList();
           
           for(Faculty f: authors){
              emails.add(f.getEmail());
              flnames.add(f.getFlname());           
           }
           String strEmails = "";
           for(String email:emails){
             strEmails+=(email+",");
           }
           //remove last comma from email
           strEmails = strEmails.substring(0, strEmails.length() - 1);
           emailField.setText(strEmails);
           
           String strFlNames = "";
           for(String flname:flnames){
              strFlNames = strFlNames + (flname + "\n");
           }           
           authorTextArea.setText(strFlNames);
           abstractTextArea.setText(p.getAbstract());    
        }
     }
   
     
  }
  public void mouseEntered(MouseEvent e){}
  public void mouseExited(MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mouseClicked(MouseEvent e){}
}//END PaperUI