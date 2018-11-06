package datumizeclient.component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

//This class is based on a post at the following URL:
//https://codereview.stackexchange.com/questions/39123/synchronous-and-asynchronous-behavior-for-client

public abstract class AbstractRemoteCall<T> implements RemoteCall<T> {

	private final ExecutorService executor;

	public AbstractRemoteCall(ExecutorService executor) {
		this.executor = executor;
	}
	
	
	// note, final so it cannot be overridden in a sub class.
	// note, conn is final so it can be passed to the callable.
	/**
	 * Method to execute the request asynchronously. The method
	 * creates a Callable from the synchronous method.
	 * 
	 * @param conn
	 * @return Future
	 * @throws IOException
	 */
	public final Future<T> executeAsynchronous(final HttpURLConnection conn) {
		App.logger.info("Execute Asynchronous method called.");
		Callable<T> task = new Callable<T>() {
			
			public T call() throws IOException  {
				App.logger.info("Execute Asynchronous method created Callable task.");
				return executeSynchronous(conn);
			}

		};
		App.logger.info("Execute Asynchronous method submitting task to Executor.");
		return executor.submit(task);
	}
}
