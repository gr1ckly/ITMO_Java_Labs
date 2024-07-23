package validation.exceptions;

import java.io.File;

/**
 * Класс-наслденик {@link Exception}, предупреждающий о некорректном заголовке csv-документа.
 */
public class CSVInvalidTitleException extends Exception{
    public CSVInvalidTitleException(File file){
        super("Файл " + file.getName() + " имеет неверный заголовок таблицы.");
    }
}
