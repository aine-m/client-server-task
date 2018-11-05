package datumizeserver.component;

import java.nio.charset.StandardCharsets;

public class AppService {

	private static float totalValue = 0;

	/**
	 * Converts the HTTP request body to a float and adds it to the totalValue
	 * field. The updated value is returned to the calling function to make up the
	 * HTTP response body.
	 * 
	 * @param requestBody
	 * @return byte array for HTTP response body
	 */
	public byte[] handlePost(byte[] requestBody) {
		String requestString = new String(requestBody, StandardCharsets.UTF_8).trim();
		float addVal = Float.parseFloat(requestString);
		totalValue += addVal;
		return String.valueOf(totalValue).trim().getBytes();
	}

	/**
	 * Returns the current value of the totalValue field.
	 * 
	 * @return byte array for HTTP response body
	 */
	public byte[] handleGet() {
		App.logger.info("CURRENT GET VALUE: " + String.valueOf(totalValue));
		return String.valueOf(totalValue).trim().getBytes();
	}

}
