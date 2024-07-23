package validation;


import commonModule.validation.Validator;
import model.converters.ConverterCSV;

/**
 * Класс-наследник {@link Validator}, содержащий методы для проверки валидности csv-документа..
 */
public class CSVValidator implements Validator<String, String> {
    public String separator;
    public ConverterCSV converter;
    public CSVValidator(String separator){
        this.separator = separator;
        this.converter = new ConverterCSV(separator);
    }

    /**
     * Метод, проверяющий корректность заголовка таблицы.
     * @param title - {@link String} - заголовок таблицы из файла.
     * @param Tclass - {@link Class}, для которого содержится информация в таблице.
     * @return boolean
     */
    public boolean isTitleValid(String title, Class Tclass){
        if (title.equals(this.converter.getCSVTitle(Tclass))){
            return true;
        }
        return false;
    }

    /**
     * Метод, проверяющий что в строке из csv-документа имеется достаточное количество аргументов для создания объекта.
     * @param title - {@link String} с заголовком таблицы.
     * @param element - строка с данными для создания элемента.
     * @return boolean
     */
    @Override
    public boolean isValid(String title, String element){
        if (title.split(this.separator).length == element.replace("/;", "/").replace(";", "2;").split(this.separator).length){
            return true;
        }
        return false;
    }
}
