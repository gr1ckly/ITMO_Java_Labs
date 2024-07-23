package controller.exceptions;

/**
 * Класс-наслденик {@link Exception}, использующийся для обозначения того, что определение должно быть положительным.
 */
public class PositiveValueException extends Exception{
    public PositiveValueException(String message){
        super(message);
    }
}
