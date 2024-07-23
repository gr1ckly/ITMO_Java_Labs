package controller.exceptions;

/**
 * Класс-наслденик {@link Exception}, используюшийся для предупреждения о недопустимой рекурсии.
 */
public class RecursionException extends Exception{
    public RecursionException(String message){
        super(message);
    }
}
