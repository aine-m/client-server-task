package datumizeclient.component;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class App {
	static Logger logger = Logger.getLogger(App.class.getName());
	static FileHandler fh;
	static AppClient client;

	public static void main(String[] args) {
		int sum = 0;
		startLog();
		client = new AppClient();
		try {
			for(int i = 1; i < 10; i++) {
				client.add(i);
				client.get();
				sum+=i;
				logger.info("SUM VALUE: " + String.valueOf(sum));
			}
		}catch(Exception e) {
			logger.warning("Exception in Client main method!");
			logger.warning(e.getMessage());
		}
		
		
//		ConfigSettings config = new ConfigSettings();
//		//set default config to synchronous
//		boolean runAsync = false;
//		try {
//			runAsync = config.getPropValues();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		AppClient client = new AppClient();
//
//		if (runAsync) {
//			try {
//				logger.info("Running asynchronously");
//				client.executeAsynchronous(RequestTypes.POST);
//				client.executeAsynchronous(RequestTypes.GET);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//			try {
//				logger.info("Running synchronously");
//				client.executeSynchronous(RequestTypes.POST);
//				client.executeSynchronous(RequestTypes.GET);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

	}
	
	public static void startLog() {
		try {
			// This block configure the logger with handler and formatter
			fh = new FileHandler("C:/tmp/DatumizeClient.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			// the following statement is used to log any messages
			logger.info("Starting Client log...");

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
