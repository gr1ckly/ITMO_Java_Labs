package model;

/**
 * Базовый интерфейс для создания хранилища
 * @param <E>
 * @param <T>
 */
public interface Storage<E, S, T, U> {
    public E add (T object);
    public E remove(S t, U u);
    public E clear(U u);
    public E show();
}
