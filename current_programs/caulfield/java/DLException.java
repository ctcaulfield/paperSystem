import java.sql.*;
import java.util.*;
import java.io.*;
import java.lang.*;

public class DLException extends Exception{

   
   Exception excep;
   private ArrayList<String> stringList = new ArrayList<String>();
      
   public DLException(Exception excep){
      this.excep = excep;   
   }//end of constr 1
   
   //excep - exception that was caught
   //String message of the exception
   public DLException(Exception excep,String... values){
       this.excep = excep;
       stringList = new ArrayList<String>(Arrays.asList(values));
   } //end of constr 2
   
   //write the exception message and String message to log
   public void log(){
      //add the exception to the list
      String exceptionMessage = excep.getMessage() + "\n";
      StackTraceElement[] trace = excep.getStackTrace();
      for (int i=0;i<trace.length;i++) {
         exceptionMessage += trace[i].toString() + "\n";
      }
      
      
		try {
			File file = new File("output.txt");
			FileWriter fileWriter = new FileWriter(file);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			
         for(String exception: stringList){
            printWriter.print(exception);
         }
         printWriter.print(exceptionMessage);
         
			fileWriter.flush();
			fileWriter.close();
		} 
      
      catch (IOException e) {
			e.printStackTrace();
		}
      
      
   }//end of log



} //end of DLException