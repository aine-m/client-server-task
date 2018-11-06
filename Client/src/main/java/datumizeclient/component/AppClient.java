package datumizeclient.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//https://codereview.stackexchange.com/questions/39123/synchronous-and-asynchronous-behavior-for-client

public class AppClient extends AbstractRemoteCall<String> {

	private final String baseUrl = "http://localhost:8080";
	public boolean runAsync = false;

	/**
	 * Creates a maximum of numThreads threads that are reused when available.
	 */
	public AppClient(int numThreads) {
		super(Executors.newFixedThreadPool(numThreads));
		try{
			getConfig();
		}catch(NullPointerException e) {
			App.logger.warning("Exception occurred when locating config.properties file. \nEnsure file is added to classpath.\nRunning in Synchronous mode.");
		}
	}

	/**
	 * Synchronous call to the add() method
	 * 
	 * @param addVal
	 * @return String 
	 * @throws IOException
	 * @throws Exception
	 */
	public String addSync(float addVal) throws IOException, Exception {
		HttpURLConnection con = createPostConn(addVal);
		String value = executeSynchronous(con);
		App.logger.info("Synchronous add method running...adding value = " + addVal);
		return value;
	}

	/**
	 * Asynchronous call to the add() method. 
	 * 
	 * @param addVal
	 * @return Future<String> 
	 * @throws IOException
	 * @throws Exception
	 */
	public Future<String> addAsync(float addVal) throws IOException {
		App.logger.info("Asynchronous add method running...adding value = " + addVal);
		HttpURLConnection con = createPostConn(addVal);
		Future<String> futureVal = executeAsynchronous(con);
		return futureVal;
	}

	/**
	 * Synchronous call to get()
	 * 
	 * @return String
	 * @throws IOException
	 * @throws Exception
	 */
	public String getSync() throws IOException {
		HttpURLConnection con = createGetConn();
		String value = executeSynchronous(con);
		App.logger.info("Synchronous get method running...");
		return value;
	}

	/**
	 * Asynchronous call to get()
	 * 
	 * @return Future<String>
	 * @throws IOException
	 * @throws Exception
	 */
	public Future<String> getAsync() throws IOException, Exception {
		HttpURLConnection con = createGetConn();
		Future<String> futureVal = executeAsynchronous(con);
		while (!futureVal.isDone()) {
			App.logger.info("Asynchronous get method running...");
		}
		return futureVal;
	}

	/**
	 * @throws IOException 
	 * 
	 */
	public String executeSynchronous(HttpURLConnection con) throws IOException  {
		App.logger.info("Execute Synchronous method called.");
		String response;
		con.connect();
		response = getResponseBody(con);
		return response;
	}

	/**
	 * Create a GET url
	 * 
	 * @return HttpURLConnection
	 * @throws MalformedURLException 
	 * @throws Exception
	 */
	private HttpURLConnection createGetConn() throws MalformedURLException, IOException {
		App.logger.info("Creating GET url");
		String urlString = baseUrl.concat("/GetValue");
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("GET");
		App.logger.info("GET url created");
		return con;
	}

	/**
	 * Create a POST url with a float value in the request body
	 * 
	 * @param addVal
	 * @return HttpURLConnection
	 * @throws Exception
	 */
	private HttpURLConnection createPostConn(float addVal) throws MalformedURLException, IOException {
		App.logger.info("Creating POST url with float addVal=" + addVal);
		String urlString = baseUrl.concat("/AddValue");
		URL url = new URL(urlString);

		String urlParams = String.valueOf(addVal);
		byte[] postData = urlParams.getBytes(StandardCharsets.UTF_8);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("charset", "utf-8");
		con.setRequestProperty("Content-Length", Integer.toString(postData.length));
		con.setDoOutput(true);
		con.getOutputStream().write(postData);
		App.logger.info("POST url created");
		return con;
	}

	/**
	 * Retrieve configuration settings for client.
	 */
	private void getConfig() {
		App.logger.info("Retrieving configuration settings");
		ConfigSettings config = new ConfigSettings();
		try {
			runAsync = config.getPropValues();
		} catch (IOException e) {
			App.logger.info("Exception occurred when getting configuration settings.  Please check the config.properties file.");
			App.logger.info(e.getMessage());
			e.printStackTrace();
		}
	}

	
	/**
	 * Method to get the response body from the HTTP request response.
	 * 
	 * @param conn
	 * @return String
	 * @throws IOException
	 */
	private String getResponseBody(HttpURLConnection conn) throws IOException {
		int responseCode = conn.getResponseCode();
		InputStream inputStream;
		if (200 <= responseCode && responseCode <= 299) {
			inputStream = conn.getInputStream();
		} else {
			inputStream = conn.getErrorStream();
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder response = new StringBuilder();
		String currentLine;

		while ((currentLine = br.readLine()) != null)
			response.append(currentLine);

		br.close();
		return response.toString();
	}

}
