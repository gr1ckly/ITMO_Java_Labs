package validation.exceptions;

import java.io.File;

/**
 * Класс-наслденик {@link Exception}, предупреждающий об отсутствии заголовка в csv-документе.
 */
public class CSVNotTitleException extends Exception{
    public CSVNotTitleException(File file){
        super("Файл " + file.getName() + " не имеет заголока таблицы.");
    }
}
