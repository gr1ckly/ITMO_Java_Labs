package commonModule.requests;

import commonModule.commands.CommandType;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Класс-наслденик {@link AbstractRequest}, содержащий в себе данные для отправления запроса.
 * @param <T>
 */
public class Request<T> extends AbstractRequest{
    protected T element;
    public Request(CommandType commandType, String userName) throws NotAuthorizationException {
        super(commandType, userName);
    }
    public Request(CommandType commandType, String userName, T t) throws NotAuthorizationException {
        super(commandType, userName);
        this.element = t;
    }

    public T getElement(){
        return this.element;
    }

    public void setElement(T element) {
        this.element = element;
    }
}
