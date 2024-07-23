package commonModule.utils;

import java.io.IOException;

/**
 * Базовый интерфейс для создания объекта, который будет запускать выполнение приложения.
 */
public interface App {
    public void launch() throws IOException, ClassNotFoundException;
}
