import java.sql.*;
import java.util.*;

//test connection of MySQL in Java

public class MySQLDatabase{

   public String uri;
   private String driver;
   public String user;
   public String password;
   public Connection conn;
   private String query;
   Statement stmt = null;
   
   



   public MySQLDatabase(String uri,String user,String password){
      driver = "com.mysql.jdbc.Driver";
      this.uri = uri;
      this.user = user;
      this.password = "23mjkgcc23cc";
   } //end of constructor

   public void MySQLdbConnect() throws DLException{
      //load the driver
      try{
         Class.forName(driver);
         System.out.println("Driver loaded");
      }
      catch(ClassNotFoundException cnfe){
         throw new DLException(cnfe, "cannot find or load driver: "+ driver);
      }
      catch (Exception e) {
         throw new DLException(e, "generic");
      }
      
      //Open the MySQL database connection
      
      try{
         conn = DriverManager.getConnection(uri,user,password);
         System.out.println("\n MySQL database open");
      }
      catch(SQLException sqle){
         throw new DLException(sqle, "Could not connect to db: "+uri);
         //System.exit(2);
      }   
      catch (Exception e) {
         throw new DLException(e, "generic");
      }
   } //end of connect to database

      
   public void MySQLdbDisconnect() throws DLException{
      //Close the database connection
      try{
         conn.close();
         System.out.println("MySQL Database closed");
      }
      catch(SQLException sqle){
         throw new DLException(sqle, "Could not load MySQL Database");
         //System.exit(3);
      }
      catch (Exception e) {
         throw new DLException(e, "generic");
      }
  
   }//end of disconnect from the database
   
   //get data - takes in one parameter.
   public ArrayList<ArrayList<String>> getData(String query) throws DLException{
      this.query = query;
      ArrayList<ArrayList<String>>table = new ArrayList<>();
      try{
         stmt = conn.createStatement();
         //takes in the query provided by the user
         ResultSet rs = stmt.executeQuery(query); 

         //total columns in ResultSet
         ResultSetMetaData rsmd = rs.getMetaData();
         int totalColumns = rsmd.getColumnCount();
         
         //loops through each until no more data
         while(rs.next()){
            ArrayList<String> row = new ArrayList<String>();
            for( int i = 1; i<=totalColumns; i++){
               row.add(rs.getString(i));
            }  
            table.add(row);  
         }
   
      }
      
      catch (Exception e) {
         throw new DLException(e);
      }

      return table;


   } //end of getData
   
   
   //getData - takes in two parameters
   public ArrayList<ArrayList<String>> getData(String query, boolean displayColumns) throws DLException{
      this.query = query;
      ArrayList<ArrayList<String>>table = getData(query);
      try{         
         //adds Column name array list row to the final 2d array list
         if(displayColumns){
            table.add(0,getColumnNames());
         }

      }
      
      catch (Exception e) {
         throw new DLException(e);
      }

      return table;


   } //end of getData
   
   public ArrayList<ArrayList<String>> getData(String query, ArrayList<String> parameterValues) throws DLException{
      ArrayList<ArrayList<String>> table = new ArrayList<>();
          
      try{
         PreparedStatement ps = prepare(query,parameterValues); 
         ResultSet rs = ps.executeQuery();
         
         
         //total columns in ResultSet
         ResultSetMetaData rsmd = rs.getMetaData();
         int totalColumns = rsmd.getColumnCount();
         
         //add column names   
         ArrayList<String> columnNames = new ArrayList<String>();              
         for (int i = 1; i <= totalColumns; i++)
         {
            
            String name = rsmd.getColumnName(i);
            
            columnNames.add(name);
                        
         }  
        
         //loops through each until no more data - adds data under columns
         while(rs.next()){
            ArrayList<String> row = new ArrayList<String>();
            for( int i = 1; i<=totalColumns; i++){
               row.add(rs.getString(i));
            }  
            table.add(row);  
         }
         table.add(0,columnNames);
         
      } 
      catch (Exception e) {
         table = null;
         throw new DLException(e);
      } 
      return table;
   }
   
   
   
   public String getMetaDataInfo(String query) throws DLException{
      this.query = query;
      String mdInfo;
      try{
         stmt = conn.createStatement();
         //takes in the query provided by the user
         ResultSet rs = stmt.executeQuery(query); 

         //total columns in ResultSet
         ResultSetMetaData rsmd = rs.getMetaData();
         int totalColumns = rsmd.getColumnCount();
         mdInfo = String.format("%d fields were retrieved\n",totalColumns);
         
                 
         for (int i = 1; i <= totalColumns; i++)
         {
            
            String name = rsmd.getColumnName(i);
            String type = rsmd.getColumnTypeName(i);
            
            mdInfo += String.format("%s, %s \n",name,type);
            
         }
         
      }      
      catch (Exception e) {
         throw new DLException(e);
      }
      
      return mdInfo;
      
   } //end of getMetaDataInfo
   
   //returns the column names from the data
   public ArrayList<String> getColumnNames() throws DLException{
      this.query = query;
      ArrayList<String> columnNames = new ArrayList<String>();         

      try{
         stmt = conn.createStatement();
         //takes in the query provided by the user
         ResultSet rs = stmt.executeQuery(query); 

         //total columns in ResultSet
         ResultSetMetaData rsmd = rs.getMetaData();
         int totalColumns = rsmd.getColumnCount();
                 
         for (int i = 1; i <= totalColumns; i++)
         {
            
            String name = rsmd.getColumnName(i);
            columnNames.add(name);
                        
         }
         
      }      
      catch (Exception e) {
         throw new DLException(e);
      }
      
      return columnNames;
      
   } //end of getMetaDataInfo
   
   
   //setData
   public boolean setData(String query) throws DLException{
      try{
         this.query = query;
         stmt = conn.createStatement();
         stmt.executeUpdate(query);
         //the process made it through
         return true;
      }
      catch(SQLException se){
         System.out.println(se.getMessage());
         return false;
      }
      catch (Exception e) {
         throw new DLException(e);
      }
   } //end of setData



   public PreparedStatement prepare(String query, ArrayList<String> parameterValues) throws DLException{
      PreparedStatement ps;
      try{
         ps = conn.prepareStatement(query);
         int i=1;
         for(String param : parameterValues){
            ps.setString(i,param);
            i++;
         }
      }
      catch (Exception e) {
         throw new DLException(e);
      }
      return ps;
   }


   public boolean setData(String query, ArrayList<String> parameterValues) throws DLException{
      try{
         PreparedStatement ps = prepare(query,parameterValues);
         ps.executeUpdate();
         return true;
      }
      catch(SQLException se){
         System.out.println(se.getMessage());
         return false;
      }
      catch (Exception e) {
         
         throw new DLException(e);
      }
   }
   
   //professor Floesser said this method will be used at a later time
   //so I am creating it for now as a place holder for now.
   //It will be used at a later time like he said during class.
   public ResultSet executeStmt(String query, ArrayList<String> parameterValues) throws DLException{
      
      PreparedStatement ps;
      ResultSet rs;
      
      try{
      
         ps = conn.prepareStatement(query);
         rs = ps.executeQuery();
      }
      catch (Exception e) {
         throw new DLException(e);
      }    
      return rs;
   }
   
   //to start a transaction block
   public void startTrans() throws DLException{

      try {
         conn.setAutoCommit(false);
      }
   
      catch(Exception e) {
         throw new DLException(e);
      }   
  
   }
   
   //to end a transaction block
   public void endTrans() throws DLException{
   
      try {
         conn.commit();
      }
   
      catch(Exception e) {
         throw new DLException(e);
      }     
   }
   
   public void rollbackTrans() throws DLException{
   
      try {
         System.err.print("Transaction is being rolled back");
                conn.rollback();
      }
   
      catch(Exception e) {
         throw new DLException(e);
      }
   
   }
   
}//MySQLDatabase

