package validation.exceptions;

/**
 * Класс-наслденик {@link Exception}, предупреждающий о некорректном имени файла.
 */
public class InvalidFileNameException extends Exception{
    public InvalidFileNameException(){
        super("Некорректное имя файла.");
    }
}
