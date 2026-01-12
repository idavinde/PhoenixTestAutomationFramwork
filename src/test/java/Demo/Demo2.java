package Demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo2 {
	
	private static Logger logger = LogManager.getLogger(Demo.class);
	public static void main(String[] args) {
		
		logger.info("Inside the main method");
		int a=10;
		logger.info("Value of a {}",a);
		int b=0;
		
		
		if(b==0) {
			
			logger.warn("Value of b {}",b);
		}
		else
		{
			logger.warn("Value of b {}",b);
		}
		
		try {
			int result = a/b;
			logger.info("Value of result {}",result);
		}
		catch(ArithmeticException e) {
			
			logger.error("operation cannot happen",e);
		}
		
		
	}
}
