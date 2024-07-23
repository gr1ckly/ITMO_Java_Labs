package validation.exceptions;

/**
 * Класс-наслденик {@link Exception}, предупреждающий о том, что объект имеет невалидные значения полей.
 */
public class ElementNotValidException extends Exception{
    public ElementNotValidException(){
        super("Елемент имеет невалидные значения полей.");
    }
}
