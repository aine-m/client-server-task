package datumizeserver.component;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class App {
	static Logger logger = Logger.getLogger(App.class.getName());

	public static void main(String[] args) {
		startLog();
		AppServer.runServer(new AppController(), 8080);
	}

	public static void startLog() {
		FileHandler fh;
		try {
			fh = new FileHandler("C:/tmp/DatumizeServer.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			logger.info("Starting Server log...");

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
