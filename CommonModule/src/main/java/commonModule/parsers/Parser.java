package commonModule.parsers;

/**
 * Базовый интерфейс для создания парсеров
 * @param <T>
 * @param <S>
 * @param <U>
 */
public interface Parser<T, S, U>{
    public T parse(S s, U u);
}