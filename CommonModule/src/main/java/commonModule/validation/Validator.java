package commonModule.validation;

/**
 * Базовый интерфейс, используемый для создания валидаторов.
 * @param <T>
 * @param <S>
 */
public interface Validator<T, S>{
    public boolean isValid(T t, S s);
}
