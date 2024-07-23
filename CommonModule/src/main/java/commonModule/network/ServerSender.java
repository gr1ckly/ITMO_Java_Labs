package commonModule.network;

import java.io.IOException;

public interface ServerSender<T, S> {
    public void send(T t, S s) throws IOException;
}
