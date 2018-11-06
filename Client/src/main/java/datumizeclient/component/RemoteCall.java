package datumizeclient.component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.Future;

//This class is based on a post at the following URL:
//https://codereview.stackexchange.com/questions/39123/synchronous-and-asynchronous-behavior-for-client

public interface RemoteCall<T> {
	
	 // for synchronous
    public T executeSynchronous(final HttpURLConnection conn) throws IOException ;

    // for asynchronous
    public Future<T> executeAsynchronous(final HttpURLConnection conn);
    
}
