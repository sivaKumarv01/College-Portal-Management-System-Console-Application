package controller.collegeManagementSystem;
/*
 * import java.util.logging.Logger;
 * 
 * public class LoggerService { private static final Logger logger =
 * Logger.getLogger(LoggerService.class.getName());
 * 
 * public static Logger getLogger() { return logger; } }
 */
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
public class LoggerService {
	private static final Logger logger = Logger.getLogger(LoggerService.class.getName());
	
	 static{
	        try {
	            FileHandler fileHandler = new FileHandler(".\\src\\com.collegeManagementSystem.files\\logger.log"); 
	            fileHandler.setFormatter(new SimpleFormatter()); 
	            logger.addHandler(fileHandler);
	            logger.setLevel(Level.ALL); 
	        } catch (IOException exception) {
	           System.out.println(exception);
	        }
	    }
	 public static void logError(String message) {
		 logger.log(Level.SEVERE,message);
	 }
	 public static void logWarning(String message)
	 {
		 logger.log(Level.WARNING,message);
	 }
    public static void logInfo(String message) {
        logger.log(Level.INFO, message);
    }

}