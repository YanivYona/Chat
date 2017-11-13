package test.pack;
import org.apache.log4j.Logger;

public class Log4J 
{
	private static  Logger log = null;
	public static Logger getInstance()
	{
		if(log == null)
		log = Logger.getLogger(Log4J.class);
		
		return log;
	}
}
