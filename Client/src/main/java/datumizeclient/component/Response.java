package datumizeclient.component;

import java.util.concurrent.Future;

public class Response<T> {
	 
	private T value;

    private Response(T value) {
        this.set(value);
    }

    @SuppressWarnings("unchecked")
    public Response(String value) {
        this((T) value);
    }

    @SuppressWarnings("unchecked")
    public Response(Future<String> value) {
        this((T) value);
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }
}
