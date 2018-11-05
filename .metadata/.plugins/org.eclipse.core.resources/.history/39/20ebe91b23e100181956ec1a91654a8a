package datumizeclient.component;

import java.net.HttpURLConnection;
import java.util.concurrent.Future;

//https://codereview.stackexchange.com/questions/39123/synchronous-and-asynchronous-behavior-for-client

public interface RemoteCall<T> {
	
//	 // for synchronous
//    public T executeSynchronous(final RequestTypes reqType) throws Exception;
//
//    // for asynchronous
//    public Future<T> executeAsynchronous(final RequestTypes reqType);
	
	 // for synchronous
    public T executeSynchronous(final HttpURLConnection conn) throws Exception;

    // for asynchronous
    public Future<T> executeAsynchronous(final HttpURLConnection conn);
    
}
