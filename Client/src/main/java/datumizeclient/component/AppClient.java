package datumizeclient.component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

//https://codereview.stackexchange.com/questions/39123/synchronous-and-asynchronous-behavior-for-client

public class AppClient extends AbstractRemoteCall<String> {

	private final String baseUrl = "http://localhost:8080";
	private static boolean runAsync = false;
				
	/**
	 * Creates a maximum of 10 threads that are reused when available. The number of
	 * threads can be changed.
	 */
	public AppClient() {
		super(Executors.newFixedThreadPool(10));
		getConfig();
	}

	
	public Response<Object> add(float addVal) throws IOException, Exception {
		HttpURLConnection con = createPostConn(addVal);
		if(runAsync) {
			return new Response<>(executeAsynchronous(con));
		}else {
			return new Response<>(executeSynchronous(con));
		}	
	}
	
	public  Response<Object> get() throws IOException, Exception {
		HttpURLConnection con = createGetConn();
		if(runAsync) {
			return new Response<>(executeAsynchronous(con));
		}else {
			return new Response<>(executeSynchronous(con));
		}
	}
	
	
//	/**
//	 * Accepts the request type (GET or POST) and creates the appropriate
//	 * HttpURLConnection. The connection is made to the server and response
//	 * returned.
//	 * 
//	 * For the post request, the float value is created from a random number
//	 * generator.
//	 */
//	public String executeSynchronous(RequestTypes requestType) throws Exception {
//		HttpURLConnection con;
//		String response;
//		
//		if (requestType.equals(RequestTypes.GET)) {
//			con = createGetConn();
//		} else if (requestType.equals(RequestTypes.POST)) {
//			//create random float value
//			Random rand = new Random();
//			float addVal = rand.nextFloat() * 10;
//			con = createPostConn(addVal);
//		} else {
//			URL url = new URL(baseUrl);
//			con = (HttpURLConnection) url.openConnection();
//		}
//
//		App.logger.info("About to make the URL connection");
//		con.connect();		
//		response = Integer.toString(con.getResponseCode());
//		response.concat(con.getResponseMessage());
//		return response;
//	}
//	
	public String executeSynchronous(HttpURLConnection con) throws IOException {
		App.logger.info("Running synchronous mode");
		String response;
		con.connect();
		response = Integer.toString(con.getResponseCode());
		response.concat(con.getResponseMessage());
		return response;
	}

	/**
	 * Create a GET url
	 * @return HttpURLConnection
	 * @throws Exception
	 */
	private HttpURLConnection createGetConn() throws Exception {
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
	 * @param addVal
	 * @return HttpURLConnection
	 * @throws Exception
	 */
	private HttpURLConnection createPostConn(float addVal) throws Exception {
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
	
	private void getConfig() {
		ConfigSettings config = new ConfigSettings();
		try {
			runAsync = config.getPropValues();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	

}