package datumizeclient.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigSettings {

	boolean runAsync = false; // set default client to run in synchronous mode
	InputStream inputStream = null;

	/**
	 * Read the config.properties file and determine whether client is to run
	 * synchronously or asynchronously
	 * 
	 * @return boolean
	 */
	public boolean getPropValues() throws IOException {
		App.logger.info("Retrieving configuration properties");
		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				App.logger.warning("Property file '" + propFileName
						+ "' not found in the classpath.  Client will be run in synchronous mode.");
			}
			runAsync = Boolean.parseBoolean(prop.getProperty("runAsync"));

		} catch (Exception e) {
			App.logger.warning("Exception when retrieving configuration properties");
			App.logger.warning(e.getMessage());
		} finally {
			inputStream.close();
		}

		App.logger.info("Configuration properties updated. Run asynchronously is set to " + runAsync);
		return runAsync;
	}
}
