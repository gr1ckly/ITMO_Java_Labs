package model.writers;

import java.io.IOException;

/**
 * Базовый интерфейс для записи данных
 * @param <T>
 * @param <S>
 */
public interface Writer<T, S> {
    public S write(T t) throws IOException;
}
