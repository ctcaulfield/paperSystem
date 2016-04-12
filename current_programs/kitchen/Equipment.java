import java.util.ArrayList;

/**
 * @author Ian Kitchen
 *
 * This class is a data layer object that represents the contents of the 
 * Equipment table in the travel database
 * 
 * Revisions:
 * 		1.0 - Created class and added constructors and fetch, put, post and 
 *            delete methods
 */
public class Equipment {
	
	private int equipID = 0;           //equipID attribute
	private String equipName = "";     //equipmentName attribute
	private String equipDescript = ""; //equipmentDescription attribute
	private int equipCapacity = 0;     //equipmentCapacity attribute
	private MySQLDatabase mysqldb;     //the database object
	
	/**
	 * Default constructor
	 */
	public Equipment() {
		mysqldb = new MySQLDatabase();
		
	}//END constructor
	
	/**
	 * Constructor that sets a default equipmentID attribute
	 * 
	 * @param equipID - the equipmentID number
	 * @param mysqldb - the database object
	 */
	public Equipment(int equipID) {
		this.equipID = equipID;
		mysqldb = new MySQLDatabase();
		
	}//END constructor2
	
	/**
	 * A constructor that sets all of the attributes
	 * 
	 * @param equipID - equipmentID number
	 * @param equipName - equipment name
	 * @param equipDescript - equipment description
	 * @param equipCapacity - equipment capacity
	 */
	public Equipment(int equipID, String equipName, String equipDescript, 
			int equipCapacity) {
		this.equipID = equipID;
		this.equipName = equipName;
		this.equipDescript = equipDescript;
		this.equipCapacity = equipCapacity;
		mysqldb = new MySQLDatabase();
		
	}//END constructor3
	
	/**
	 * Mutator to set the equipmentID attribute
	 * 
	 * @param ID - the new equipmentID
	 */
	public void setID(int ID) {
		equipID = ID;
		
	}//END setID
	
	/**
	 * Mutator to set the equipment name attribute
	 * 
	 * @param name - the new equipment name
	 */
	public void setName(String name) {
		equipName = name;
		
	}//END setName
	
	/**
	 * Mutator to set the equipment description attribute
	 * 
	 * @param descript - the new equipment description
	 */
	public void setDescription(String descript) {
		equipDescript = descript;
		
	}//END setDescription
	
	/**
	 * Mutator to set the equipment capacity attribute
	 * 
	 * @param capacity - the new equipment capacity
	 */
	public void setCapacity(int capacity) {
		equipCapacity = capacity;
		
	}//END setCapacity
	
	/**
	 * Accessor for equipmentID attribute
	 * 
	 * @return the current equipmentID
	 */
	public int getID() {
		return equipID;
		
	}//END getID
	
	/**
	 * Accessor for the equipment name attribute
	 * 
	 * @return the current equipment name
	 */
	public String getName() {
		return equipName;
		
	}//END getName
	
	/**
	 * Accessor for the equipment description attribute
	 * 
	 * @return the current equipment description
	 */
	public String getDescription() {
		return equipDescript;
		
	}//END getDescription
	
	/**
	 * Accessor for the equipment capacity attribute
	 * 
	 * @return the current equipment capacity
	 */
	public int getCapacity() {
		return equipCapacity;
		
	}//END getCapacity
	
	/**
	 * fetch() will use this object's equipmentID attribute and the 
	 * MySQLDatabase class' getData() method to retrieve the database values 
	 * for that particular equipmentID and update the other attributes 
	 * accordingly.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean fetch() {
		
		boolean doesFetch = false;
		
		//USE this string for a correct select statement
        String stmnt = "SELECT equipmentname, equipmentdescription, "
        		+ "equipmentcapacity FROM equipment WHERE equipID = ?";
        ArrayList<String> values = new ArrayList<>(1);
        values.add(Integer.toString(equipID));
        
        try {
			mysqldb.connect();
		    //execute the SELECT statement and get the results
		    ArrayList<ArrayList> data = mysqldb.getData(stmnt, values);
			
			//loop through the data array and set the attributes
		    if(data.size() != 0) {
				for(ArrayList<String> d: data) {	
					for(int i=0; i < d.size(); i++) {
						if(i == 2)
							setCapacity(Integer.parseInt(d.get(i)));
						
						else if(i == 1)
						    setDescription(d.get(i));
						
						else
							setName(d.get(i));
					}
				}
				doesFetch = true;
			}
		    else {
		    	//the data returned an empty set, set all attributes to defaults
		    	setID(0);
		    	setName("");
		    	setDescription("");
		    	setCapacity(0);
		    }
		    mysqldb.close();
				
		} catch (DLException e) {
			System.out.println("Could not complete operation. "
					+ "Please check log file");
		}
        
        return doesFetch;
	}//END fetch
	
	/**
	 * put() will update the database values, for this object's equipmentID, 
	 * using all this object's attributes (name, description, capacity).
	 */
	public void put() {
		String stmnt = "UPDATE equipment SET equipmentname = '" + equipName 
				+ "', equipmentdescription = '" + equipDescript
				+ "', equipmentcapacity = " + equipCapacity 
				+ " WHERE equipID = ?";
		ArrayList<String> values = new ArrayList<>(1);
        values.add(Integer.toString(equipID));
		
		try {
			mysqldb.connect();
			mysqldb.setData(stmnt, values);
			mysqldb.close();
				
		} catch (DLException e) {
			System.out.println("Could not complete operation. "
					+ "Please check log file");
		}		
	}//END put
	
	/**
	 * post() will insert this object's attribute values as a new record into 
	 * the database.
	 */
	public void post() {
		String stmnt = "INSERT INTO equipment VALUES (" 
				+ equipID + ",'" + equipName + "','" + equipDescript + "',"
				+ equipCapacity + ")";
		
		try {
			mysqldb.connect();
			mysqldb.setData(stmnt);
			mysqldb.close();
				
		} catch (DLException e) {
			System.out.println("Could not complete operation. "
					+ "Please check log file");
		}
	}//END post
	
	/**
	 * delete() will remove from the database any data corresponding to this
	 * object's equipmentID
	 */
	public void delete() {
		String stmnt = "DELETE FROM equipment WHERE equipID = " + equipID;
		
		try {
			mysqldb.connect();
			mysqldb.setData(stmnt);
			mysqldb.close();
				
		} catch (DLException e) {
			System.out.println("Could not complete operation. "
					+ "Please check log file");
		}	
	}//END delete
	
	/**
	 * Swaps the equipment name of this equipment object with another
	 * 
	 * @param otherID - the ID of the other equipment object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void swap(int otherID) {
		ArrayList<ArrayList> name = new ArrayList<>();
		String newName = "";
		String getName = "SELECT equipmentname FROM equipment WHERE "
				+ "equipID = " + otherID;
		String setName = "UPDATE equipment SET equipmentname = '" + equipName +
				"' WHERE equipID = " + otherID;
		
		try {
			mysqldb.connect();
			
			mysqldb.startTrans();
			
			//get the name from the object being swapped
			name = mysqldb.getData(getName);
			
			//set the other equipment object's name to this object's name
			mysqldb.setData(setName);
			
			mysqldb.endTrans();
			
			//set this object's name to the other object's previous name
			for(ArrayList<String> s: name) {
				for(String n: s) {
					newName = n;
				}
			}
			setName(newName);
			put();
			
			mysqldb.close();
				
		} catch (DLException e) {
			System.out.println("Could not complete operation. "
					+ "Please check log file");
		}
	}

}//END Equipment
