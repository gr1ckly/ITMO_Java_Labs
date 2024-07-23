package commonModule.network;

import java.io.IOException;
import java.net.SocketException;

public interface Listener<T>{
    public T listen() throws SocketException, IOException;
}
