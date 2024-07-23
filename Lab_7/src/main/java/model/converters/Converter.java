package model.converters;

/**
 * Базовый интерфейс для создания преобразователей данных из одного формата в другой.
 * @param <T>
 * @param <S>
 */
public interface Converter<T, S> {
    public T convert(S s);
}
