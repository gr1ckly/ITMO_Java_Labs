package controller;

/**
 * Базовый интерфейс для создания хранилища
 * @param <E>
 * @param <T>
 */
public interface Storage<E, S, T> {
    public E add (T object);
    public E remove(S t);
    public E clear();
    public E show();
}
