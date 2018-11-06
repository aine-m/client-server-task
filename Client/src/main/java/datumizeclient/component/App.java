package datumizeclient.component;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class App {
	static Logger logger = Logger.getLogger(App.class.getName());
	static FileHandler fh;
	static AppClient client;

	public static void main(String[] args) {
		startLog();
		client = new AppClient(10);

		if (client.runAsync) {
			try {
				Future<String> f1 = client.addAsync(1);
				Future<String> f2 = client.addAsync(2);

				while (!(f1.isDone() && f2.isDone())) {
					System.out.println(String.format("f1 is %s and f2 is %s",
							f1.isDone() ? "done" : "not done", f2.isDone() ? "done" : "not done"));
				}

				logger.info(f1.get());
				logger.info(f2.get());

			} catch (IOException | InterruptedException | ExecutionException e) {
				logger.severe("Exception occurred during client execution");
				logger.severe(e.getMessage());
				e.printStackTrace();
			} 

		}
		
		if(!client.runAsync) {
			try {
				for(int i = 1; i < 10; i++) {
					client.addSync(i);
					client.getSync();
				}
			}catch(Exception e) {
				logger.severe("Exception in Client main method!");
				logger.severe(e.getMessage());
			}
		}

	}

	/**
	 * Starts the Logger instance.  File location can be changed in the FileHandler().
	 */
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
