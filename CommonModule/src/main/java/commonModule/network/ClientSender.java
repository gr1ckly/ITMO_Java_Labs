package commonModule.network;

import java.io.IOException;

public interface ClientSender<T>{
    public void send(T t) throws IOException;
}
