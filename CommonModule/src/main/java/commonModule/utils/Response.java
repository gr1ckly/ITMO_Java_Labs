package commonModule.utils;

import java.io.*;

/**
 * Класс, объекты которого несут в себе ответ на выполнение программы.
 */
public class Response<T> implements Serializable {
    @Serial
    private static final int serialVersionUID = 49587234;
    private T element;
    public Response(T element){
        this.element = element;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }
}
