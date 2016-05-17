import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * 
 * @author Ian Kitchen <igk2718@rit.edu>
 *
 */
public class DLException extends Exception {

	private static final long serialVersionUID = -4614384053361974945L;
	private Exception e;
	private String[] message;
	
	/**
	 * 
	 * @param e
	 */
    public DLException(Exception e) {
    	this.e = e;
    	log();
    }
    
    /**
     * A constructor that also accepts an ArrayList 
     * 
     * @param e - the error message
     * @param message - any explicit reasoning
     */
    public DLException(Exception e, String... message) {
    	this.e = e;
    	this.message = message;
    	log();
    }
    
    /**
     * Writes the contents of the error and any related message to a text file
     */
    private void log() {
    	Date date = new Date();
    	File file = new File("log.txt");
    	
    	//create the content to be written to the log file
    	String content = "Timestamp: " + date.toString() + "\n" +
    			"Error: " + e.toString() + "\nMessage:\n";
    	
    	if(message.length > 0) {
	    	for(String s: message) {
	    		content += s + "\n";
	    	}
    	}
    	content += "\n";
    	//write the content to the log file
    	try {
    	    FileWriter fw = new FileWriter(file, true);
    	    
    	    fw.write(content);
    	    fw.close();
    	    
    	} catch(IOException ioe) {
    	    System.out.println("Unable to write to log file\n");
    	    ioe.printStackTrace();
    	}
    }//END log
    
}//END DLException
