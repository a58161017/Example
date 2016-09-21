package kiuno.example.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MyLogger {
	private Logger logger = null;
	
	public MyLogger(Class cls){
		PropertyConfigurator.configure("log4j.properties");
		logger = Logger.getLogger(cls);
	}
	
	public Logger getLogger(){
		return logger;
	}
}
