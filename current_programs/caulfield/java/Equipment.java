import java.sql.*;
import java.util.*;
 
public class Equipment
{
   //connection values
   private String mySQLuri = "jdbc:mysql://127.0.0.1/travel?useSSL=true";
   private String mySQLuser = "root";
   private String mySQLpassword = "";
   
   //equipment values
   private int equipID;
   private String equipmentName;
   private String equipmentDescription;
   private int equipmentCapacity;
   
   //create instance - object to connect to database
   private MySQLDatabase mySQLdb = new MySQLDatabase(mySQLuri,mySQLuser,mySQLpassword);
   
   //default constructor
   public Equipment(){
 
      //create object to connect to mySQL database
      MySQLDatabase mySQLdb = new MySQLDatabase(mySQLuri,mySQLuser,mySQLpassword);
   
   }
   
   public Equipment(int equipID){
   
      this.equipID = equipID;
   
   }
   
   public Equipment(int equipID, String equipmentName, String equipmentDescription, int equipmentCapacity){
     
      this.equipID = equipID;
      this.equipmentName = equipmentName;
      this.equipmentDescription = equipmentDescription;
      this.equipmentCapacity = equipmentCapacity;  
 
   }
   
   public int getEquipID(){
  
      return this.equipID;
  
   }
   
   public String getEquipmentName(){
   
      return this.equipmentName;
   
   }
   
   public String getEquipmentDescription(){
  
      return this.equipmentDescription;
  
   }
   
   public int getEquipmentCapacity(){
  
      return this.equipmentCapacity;
  
   }
   
   public void setEquipId(int equipId){
  
      this.equipID = equipID;
  
   }
   
   public void setEquipmentName(String equipmentName){
  
      this.equipmentName = equipmentName;
  
   }
   
   public void setEquipmentDescription(String equipmentDescription){
  
      this.equipmentDescription = equipmentDescription;
  
   }
   
   public void setEquipmentCapacity(int equipmentCapacity){
  
      this.equipmentCapacity = equipmentCapacity; 
  
   }
   
   private void beginConnection(){   
  
      //connect to the database
      try {
         mySQLdb.MySQLdbConnect();
      }
      catch(DLException e){
         e.log();
         System.exit(3);
      }
      System.out.println("Connected");
  
   } //end of begin conneciton
   
   private void endConnection(){
  
      //disconnect from the database
      try {
         mySQLdb.MySQLdbDisconnect();
      }
      catch(DLException e){
         e.log();
         System.exit(3);
      }
      System.out.println("Disconnected");
      System.out.println("****End of MySQL process****");
  
   } //end of endConnection 
   
   public void fetch(){
  
      beginConnection();
      ArrayList<String> parameterValues = new ArrayList<String>();
      
      //converts the provided int to a string so that it can be added to the
      //ArrayList of type String
      
      String strEquipID = Integer.toString(equipID);
      parameterValues.add(strEquipID);
      
      String string = String.format("select * from equipment WHERE EquipID =?");    
      try {
         ArrayList<ArrayList<String>>table = mySQLdb.getData(string, parameterValues);
         System.out.println(String.format("Processing query: %s ",string));
         if(table.isEmpty()){
            System.out.println("No data avaliable");
         }
         else{
            // print table output result
            for( ArrayList<String> row: table ){
                for( String data: row ){
                    System.out.print( " " + data );
                }
                System.out.println();
            }  
         }
      }
      catch(DLException e){
         e.log();
         System.exit(3);
      }  
      endConnection();
      
   } //end of fetch method

   public void put(){
      
      beginConnection();
      ArrayList<String> parameterValues = new ArrayList<String>();
      
      String strEquipmentCapacity = Integer.toString(equipmentCapacity);
      parameterValues.add(strEquipmentCapacity);
      
      String strequipID = Integer.toString(equipID);
      parameterValues.add(strequipID);      
      
      String string = String.format("UPDATE equipment SET equipmentCapacity =? WHERE EquipID =?");
      //if result is true
      try {
         if(mySQLdb.setData(string,parameterValues)){
            System.out.println(String.format("Successful query: %s ",string));
         }
         else{
            System.out.println("Query not valid");
         }
      }
      catch(DLException e){
         e.log();
         System.exit(3);
      } 
      endConnection();
    
   } //end of put method
   
   public void post(){
         
      beginConnection();
     
      ArrayList<String> parameterValues = new ArrayList<String>();
     
      String strEquipID = Integer.toString(equipID);
      parameterValues.add(strEquipID);
      
      parameterValues.add(equipmentName);
      
      parameterValues.add(equipmentDescription);
      
      String strEquipmentCapacity = Integer.toString(equipmentCapacity);
      parameterValues.add(strEquipmentCapacity);
     

     
      
      String string = String.format("INSERT INTO equipment value (?,?,?,?)",equipID, equipmentName, equipmentDescription, equipmentCapacity);
      try {
         if(mySQLdb.setData(string, parameterValues)){
            System.out.println(String.format("Successful query: %s ",string));
         }
         else{
            System.out.println("Query not valid");
         }
      }
      catch(DLException e){
         e.log();
         System.exit(3);
      }
      //if result is true
      endConnection();
   
   } //end of post method
   
   public void delete(){
 
      beginConnection();
      
      ArrayList<String> parameterValues = new ArrayList<String>();
 
      String strEquipID = Integer.toString(equipID);
      parameterValues.add(strEquipID);
      
      
      String string = String.format("DELETE from equipment where EquipID =?");
      //if result is true
      try {
         if(mySQLdb.setData(string, parameterValues)){
            System.out.println(String.format("Successful query: %s ",string));
         }
         else{
            System.out.println("Query not valid");
         }
      }
      catch(DLException e){
         e.log();
         System.exit(3);
      }
      endConnection();
   
    } //end of delete method
    
   private void printTable(int columnSpace,ArrayList<ArrayList<String>> table){
      StringBuilder sb=new StringBuilder();

      if(table.isEmpty()){
         System.out.println("No data avaliable");
      }
      else{
         // print table output result
         for( ArrayList<String> row: table ){
            int addedSpace = 0;
            for( String data: row ){
               addedSpace = columnSpace - data.length(); 
               sb.append(String.format("%s",data));
               int i=0;
               while(i<addedSpace){
                  sb.append(" ");
                  i++;
               }
            }//data for
                sb.append(String.format("\n"));
         }//row for
            System.out.println(sb);
      }
   
   }//end print table
   
   
   public void swap(int newEquipID){
      String equipmentName = null;
   
      beginConnection();
      ArrayList<String> parameterValues = new ArrayList<String>();      
     
      String strEquipID = Integer.toString(newEquipID);
      parameterValues.add(strEquipID);
            
      String string = String.format("select equipmentName from equipment WHERE EquipID =?");
     
      try {
         mySQLdb.startTrans();
         ArrayList<ArrayList<String>>table = mySQLdb.getData(string, parameterValues);
         System.out.println(String.format("Processing query: %s ",string));
         if(table.isEmpty()){
            System.out.println("No data avaliable");
         }
         //once the equipmentName is grabbed we are going to set it to the equipment name
         else{
            // print table output result
            for( ArrayList<String> row: table ){
                for( String data: row ){
                    equipmentName = data;
                }
            }  
         }
         
         parameterValues.clear();
         parameterValues.add(equipmentName);
         strEquipID = Integer.toString(equipID);
         parameterValues.add(strEquipID);
        
         //update made object
         string = String.format("UPDATE paper SET equipmentName =? WHERE EquipID =?");
          
         if(mySQLdb.setData(string,parameterValues)){
            System.out.println(String.format("Successful query: %s ",string));
         }
         else{
            System.out.println("Query not valid");
         }
         
         parameterValues.clear();
         parameterValues.add(this.equipmentName);
         strEquipID = Integer.toString(newEquipID);
         parameterValues.add(strEquipID);
         
         //update already existing 
         string = String.format("UPDATE equipment SET equipmentName =? WHERE EquipID =?");
          
         if(mySQLdb.setData(string,parameterValues)){
            System.out.println(String.format("Successful query: %s ",string));
         }
         else{
            System.out.println("Query not valid");
         }
         
         //transaction complete
         mySQLdb.endTrans();
      }
      catch(DLException e){
         try{
            mySQLdb.rollbackTrans();
         }
         catch(DLException de){
            de.log();
            System.exit(3);
         }        
         e.log();
         System.exit(3);
      }
      endConnection();
   } //end of swap method 
 
 
 }//end of Equipment class
