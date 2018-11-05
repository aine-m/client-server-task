package datumizeserver.component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;

import com.sun.net.httpserver.HttpServer;

public class AppServer {

	/**
	 * Creates a HttpServer and starts the server running
	 * @param controller
	 * @param port
	 */
	public static void runServer(final AppController controller, int port) {
		HttpServer server = null;
		try {
			App.logger.info("Creating server");
			server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext("/", controller);
			server.setExecutor(null);
			App.logger.info("Starting server");
			server.start();
		} catch (IOException e) {
			App.logger.severe("Unable to start server");
			App.logger.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		}
	}

}
