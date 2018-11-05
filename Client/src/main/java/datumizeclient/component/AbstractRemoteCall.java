package datumizeclient.component;

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

//	// note, final so it cannot be overridden in a sub class.
//	// note, action is reqType so it can be passed to the callable.
//	/**
//	 * Method to execute the request asynchronously. This method creates a callable
//	 * task that is run by an ExecutorService instance. A Future value is returned
//	 * by the method, allowing execution of a program to continue while the
//	 * asynchronous method will complete at some point in the future. The method
//	 * makes a call to the executeSynchronous method, as the functionality is the
//	 * same.
//	 * 
//	 */
//	public final Future<T> executeAsynchronous(final RequestTypes reqType) {
//
//		Callable<T> task = new Callable<T>() {
//
//			public T call() throws Exception {
//				return executeSynchronous(reqType);
//			}
//
//		};
//
//		return executor.submit(task);
//	}
	
	
	// note, final so it cannot be overridden in a sub class.
	// note, action is reqType so it can be passed to the callable.
	/**
	 * Method to execute the request asynchronously. This method creates a callable
	 * task that is run by an ExecutorService instance. A Future value is returned
	 * by the method, allowing execution of a program to continue while the
	 * asynchronous method will complete at some point in the future. The method
	 * makes a call to the executeSynchronous method, as the functionality is the
	 * same.
	 * 
	 */
	public final Future<T> executeAsynchronous(final HttpURLConnection conn) {
		App.logger.info("Running asynchronous mode");
		Callable<T> task = new Callable<T>() {

			public T call() throws Exception {
				return executeSynchronous(conn);
			}

		};

		return executor.submit(task);
	}
}
