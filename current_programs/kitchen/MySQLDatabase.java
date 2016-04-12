import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ian Kitchen
 *
 * This class connects to the MySQL Database. It contains methods for 
 * opening the connection and closing the connection.
 * 
 * Revisions:
 * 		1.0 - Implemented the open and close methods
 * 		1.1 - Adding the database getter and setter classes
 */
public class MySQLDatabase {
	public String uri = 
			"jdbc:mysql://127.0.0.1/travel?useSSL=false";               //path for connection
	private final String driver = "com.mysql.jdbc.Driver"; //SQL driver
	private Connection conn = null;                        //Connection attribute
	private final String user = "root";                    //DB username
	private final String password = "tronkular";           //DB password
	private final String INS = "INSERT";
    private final String UPD = "UPDATE";
    private final String DEL = "DELETE";
	Scanner stmntScan;
	
	public MySQLDatabase() {
		//Load the driver
        try {
        	Class.forName(driver);
        }
        catch(ClassNotFoundException cnfe){
        	System.out.println("Cannot find or load driver: " + driver);
        	System.exit(0);
        }
	}//END constructor
	
	/**
	 * Method used for connecting to the SQL Server Database
	 * 
	 * @return true if the connection to database is successful
	 * @throws DLException 
	 */
	public boolean connect() throws DLException {
		boolean doesConnect;
		
		//Open SQL Server database
        try {
        	conn = DriverManager.getConnection(uri, user, password);
        	doesConnect = true;
        }
        catch(SQLException sqle){
        	doesConnect = false;
        	String msg = "Could not connect to database" + uri; 
        	throw new DLException(sqle, msg);
        }
        return doesConnect;
        
	}//END connect
	
	/**
	 * getData executes a SELECT query to the MySQL database
	 * 
	 * @param statement - the SQL select statement
	 * @return 2-d ArrayList containing the results of the SELECT statement
	 * @throws DLException 
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getData(String statement) throws DLException {
		
		ResultSet results;
		ArrayList<ArrayList> resultTable = null;
		int fields;
		
		try {
			//get the result set
			results = getRS(statement);
			
			//instantiate the results table ArrayList 
			resultTable = new ArrayList<>();
			
			//get the number of fields from meta data
			ResultSetMetaData rsmd = results.getMetaData();
			fields = rsmd.getColumnCount();
			
			resultTable = populateList(results, resultTable, fields);
			
		} catch (SQLException sqle) {
			String msg = "Unable to complete operation";
			throw new DLException(sqle, msg);
		}
		
		return resultTable;
		
	}//END getData
	
	/**
	 * getData executes a SELECT query to the MySQL database and accepts a 
	 * boolean value determining whether to include column names or not
	 * 
	 * @param statement - the SQL statement
	 * @param doColumns - true if the result set should contain the column names
	 * @return 2-d ArrayList containing the results of the SELECT statement
	 * @throws DLException
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getData(String statement, boolean doColumns) 
			throws DLException {
		
		ResultSet results;
		ArrayList<ArrayList> resultTable = null;
		int fields;
		
		try {
			//get the result set
			results = getRS(statement);
			
			//instantiate the results table ArrayList 
			resultTable = new ArrayList<>();
			
			//get the number of fields from meta data
			ResultSetMetaData rsmd = results.getMetaData();
			fields = rsmd.getColumnCount();
			
			if(doColumns) {
				ArrayList<String> columnName = new ArrayList<>();
				for(int i = 1; i <= fields; i++) {
					String col = rsmd.getColumnName(i);
					
					columnName.add(col);
				}
				resultTable.add(columnName);
			}
			
			resultTable = populateList(results, resultTable, fields);
			
		} catch (SQLException sqle) {
			String msg = "Unable to complete operation";
			throw new DLException(sqle, msg);
		}
		
		return resultTable;
		
	}//END getData
	
	/**
	 * getData executes a SELECT query to the MySQL database using a prepared
	 * statement
	 * 
	 * @param stmnt - the SQL statement
	 * @param values - the values for the prepared statement
	 * @return 2-d ArrayList containing the results of the SELECT statement
	 * @throws DLException
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getData(String stmnt, ArrayList<String> values) 
			throws DLException {
		ResultSet results;
		ArrayList<ArrayList> resultTable = null;
		int fields;
		
		try {
			PreparedStatement statement = prepare(stmnt, values);

			//execute the results and store them in a ResultSet
			results = statement.executeQuery();
			
			//instantiate the results table ArrayList 
			resultTable = new ArrayList<>();
			
			//get the number of fields from meta data
			ResultSetMetaData rsmd = results.getMetaData();
			fields = rsmd.getColumnCount();
			
			resultTable = populateList(results, resultTable, fields);
			
		} catch (SQLException sqle) {
			String msg = "Unable to complete operation";
			throw new DLException(sqle, msg);
		}
		
		return resultTable;

	}//END getData
	
	/**
	 * setData will UPDATE, DELETE or INSERT data into the database
	 * 
	 * @param statement - the SQL statement
	 * @return true if the statement executed successfully
	 * @throws DLException 
	 */
	public boolean setData(String statement) throws DLException {
		
		boolean isStmnt = false;
		//get the type of statement that's passed in
		stmntScan = new Scanner(statement);
		stmntScan.useDelimiter(" ");
		String stmntType = stmntScan.next();
		
		//Check to see if statement is of a valid type
		if(stmntType.equalsIgnoreCase(INS) || stmntType.equalsIgnoreCase(UPD) 
				|| stmntType.equalsIgnoreCase(DEL)) {		
			try {
				//create SQL statement
				Statement stmnt = conn.createStatement();
				
				//execute the statement
				stmnt.execute(statement);
				
				//set the return value to true
				isStmnt = true;
				
			} catch (SQLException sqle) {
				isStmnt = false;
				String msg = "Unable to complete operation";
				throw new DLException(sqle, msg);
			}
		} else 
			//send instructions to console
			System.out.println("Use INSERT, UPDATE, or DELETE queries only.");
		
		return isStmnt;
		
	}//END setDate
	
	/**
	 * setData will UPDATE, DELETE or INSERT data into the database using a 
	 * prepared statement
	 * 
	 * @param statement - the SQL statement
	 * @param values - the values for the prepared statement
	 * @return true if the statement processed successfully
	 * @throws DLException
	 */
	public boolean setData(String statement, ArrayList<String> values) 
			throws DLException {
		boolean isStmnt = false;
		//get the type of statement that's passed in
		stmntScan = new Scanner(statement);
		stmntScan.useDelimiter(" ");
		String stmntType = stmntScan.next();
		
		//Check to see if statement is of a valid type
		if(stmntType.equalsIgnoreCase(INS) || stmntType.equalsIgnoreCase(UPD) 
				|| stmntType.equalsIgnoreCase(DEL)) {		
			try {
				//create SQL statement
				PreparedStatement stmnt = prepare(statement, values);
				
				//execute the statement
				stmnt.executeUpdate();
				
				//set the return value to true
				isStmnt = true;
				
			} catch (SQLException sqle) {
				isStmnt = false;
				String msg = "Unable to complete operation";
				throw new DLException(sqle, msg);
			}
		} else 
			//send instructions to console
			System.out.println("Use INSERT, UPDATE, or DELETE queries only.");
		
	    return isStmnt;
		
	}//END setData
	
	/**
	 * descTable will execute any MySQL select statement and return a string 
	 * with the amount of fields, and a two column report of the column name and
	 * it's column type
	 * 
	 * @param statement - the SELECT statement
	 * @return the number of fields and the two column report
	 * @throws DLException
	 */
	public String descTable(String statement) throws DLException {
		
		ResultSet results;
		int fields;
		String report = "";
		
		try {
			//get the result set
			results = getRS(statement);
			
			//get the number of fields from meta data
			ResultSetMetaData rsmd = results.getMetaData();
			fields = rsmd.getColumnCount();
			report += "Number of fields: " + fields + "\n";
			report += String.format("%-18s Column Type\n", "Column Name");
			report += String.format("%-18s -----------\n", "-----------");
			
			for(int i=1; i <= fields; i++)
		    	report += String.format("%-18s %s\n", rsmd.getColumnName(i), 
		    			rsmd.getColumnTypeName(i));
		    
			report += "\n";
			
		} catch (SQLException sqle) {
			String msg = "Unable to complete operation";
			throw new DLException(sqle, msg);
		}
		
		return report;
		
	}//END descTable
	
	/**
	 * displayResults formats the data in a result set so that it is readable 
	 * through the console
	 * 
	 * @param stmnt - the SELECT statement
	 * @return a string formatted to display the data in a result set
	 * @throws DLException
	 */
	public String displayResults(String stmnt) throws DLException {
		
		String report = "";
		int maxWidth = -1;
		ResultSet rs;
		
		try {
			//get the result set, metadata and amount of fields
			rs = getRS(stmnt);
			ResultSetMetaData rsmd = rs.getMetaData();
			int fields = rsmd.getColumnCount();
			
			//find the maximum width out of all of the columns
			for(int i = 1; i <= fields; i++) {
				int newWidth = rsmd.getColumnDisplaySize(i);
				
				if(newWidth > maxWidth) {
					maxWidth = newWidth;
					
					//Set a catch all for widths longer than 25
					if(newWidth > 28) {
						maxWidth = 28;
					}
				}
			}
			//set the column names
			for(int i = 1; i <= fields; i++) {
				String col = rsmd.getColumnName(i);
				report += col;
				for(int j = col.length(); j < maxWidth; j++) {
					report += " ";
				}
			}
			report += "\n";
			//Add dashes to separate data and column names
			for(int i = 1; i <= fields; i++) {
				String col = rsmd.getColumnName(i);
				for(int y = 0; y < col.length(); y++)
					report += "-";
				for(int j = col.length(); j < maxWidth; j++)
					report += " ";
			}
			report += "\n";
			//append the query data into the output
			while(rs.next()) {
				for(int i = 1; i <= fields; i++) {
					String data = rs.getString(i);
					report += data;
					for(int j = data.length(); j < maxWidth; j++) {
						report += " ";
					}
				}
				report += "\n";
			}			
		} catch (SQLException sqle) {
			String msg = "Unable to complete operation";
			throw new DLException(sqle, msg);
		}	
		return report;
		
	}//END displayResults
	
	/**
	 * setRS executes a SELECT statement and returns the result set of that data
	 * 
	 * @param statement - the SELECT statement to be executed
	 * @return - the result set of the select statement
	 * @throws DLException
	 */
	private ResultSet getRS(String statement) throws DLException {
		
		//create SQL statement
		Statement stmnt;
		ResultSet results = null;
		
		try {
			stmnt = conn.createStatement();

			//execute the results and store them in a ResultSet
			results = stmnt.executeQuery(statement);
			
		} catch (SQLException sqle) {
			String msg = "Unable to complete operation";
			throw new DLException(sqle, msg);
		}
		
		return results;
		
	}//END getRS
	
	/**
	 * populateList will populate the ArrayList of ArrayLists that contains the
	 * data from a mySQL query
	 * 
	 * @param rs - the ResultSet containing the data from the query
	 * @param list - the ArrayList to store ResultSet data
	 * @param fields - The count of fields in the statement
	 * @return the populated ArrayList
	 * @throws DLException
	 */
	@SuppressWarnings("rawtypes")
	private ArrayList<ArrayList> populateList(ResultSet rs, 
			ArrayList<ArrayList> list, int fields) throws DLException {
		
		//WHILE there is data in the ResultSet
		try {
			while(rs.next()) {
				ArrayList<String> row = new ArrayList<>();
				
				//FOR each piece of data in the row
			    for(int i=1; i <= fields; i++) {
			    	//add the data to the row ArrayList
				    row.add(rs.getString(i));
			    }
			    //add the row list to the results table
			    list.add(row);
			}
		} catch (SQLException sqle) {
			String msg = "Unable to complete operation";
			throw new DLException(sqle, msg);
		}
		
		return list;
		
	}//END populateList
	
	/**
	 * Creates an SQL prepared statement and returns that statement
	 * 
	 * @param stmnt - initial SQL statement
	 * @param values - the different values being queried
	 * @return - the prepared statement
	 * @throws DLException
	 */
	public PreparedStatement prepare(String stmnt, ArrayList<String> values) 
			throws DLException {
		PreparedStatement pstmnt = null;
		try {
			//create a prepared statement
			pstmnt = conn.prepareStatement(stmnt);
			
			//trace through the list of strings
			for(int i = 1; i<= values.size(); i++) {
				//bind string to the prepared statement
				pstmnt.setString(i, values.get(i-1));
			}
		} catch (SQLException sqle) {
			String msg = "Unable to complete operation";
			throw new DLException(sqle, msg);
		}
		return pstmnt;
		
	}//END prepare
	
	/**
	 * Starts a transaction
	 * 
	 * @throws DLException
	 */
	public void startTrans() throws DLException{
		try {
			conn.setAutoCommit(false);
		} catch (SQLException sqle) {
			String msg = "Unable to complete operation";
			throw new DLException(sqle, msg);
		}
	}//END startTrans
	
	/**
	 * Ends a transaction
	 * 
	 * @throws DLException
	 */
	public void endTrans() throws DLException {
		try {
			//commit any changes
			conn.commit();
			
			//set auto commit back to true
			conn.setAutoCommit(true);
		} catch (SQLException sqle) {
			String msg = "Unable to complete operation";
			throw new DLException(sqle, msg);
		}
	}//END endTrans
	
	/**
	 * Executes a transaction rollback
	 * 
	 * @throws DLException
	 */
	public void rollbackTrans() throws DLException {
		try {
			conn.rollback();
		} catch (SQLException sqle) {
			String msg = "Unable to complete operation";
			throw new DLException(sqle, msg);
		}
	}//END rollbackTrans
	
	/**
	 * Method used for closing the connecting to the SQL Server Database
	 * 
	 * @return true if the database closed successfully
	 * @throws DLException 
	 */
	public boolean close() throws DLException {
		boolean doesClose;
		
		//Close SQL Server database
        try {
        	conn.close();
        	doesClose = true;
        }
        catch(SQLException sqle){
        	doesClose = false;
        	String msg = "Unable to complete operation";
			throw new DLException(sqle, msg);
        }      
        return doesClose;
        
	}//END close
}//END MySQLDatabase
