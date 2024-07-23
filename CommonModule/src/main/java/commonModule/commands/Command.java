package commonModule.commands;
import commonModule.requests.AbstractRequest;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Базовый интерфейс для всех команд
 * @param <T> T extends {@link AbstractRequest}
 */
public interface Command<T, S>{
    /**
     * @param request <T>
     */
    public S execute(T request) throws SocketException, IOException;
}
