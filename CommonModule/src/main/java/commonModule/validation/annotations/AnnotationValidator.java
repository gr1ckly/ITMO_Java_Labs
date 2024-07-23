package commonModule.validation.annotations;

import commonModule.validation.Validator;

import java.lang.reflect.Field;
import java.util.LinkedList;

/**
 * Класс-наследник {@link Validator}, реализующий валидацию на основе аннотаций.
 */
public class AnnotationValidator implements Validator<Field, Object> {

    private NonNullValidator nonNullValidator;
    private MaxValidator maxValidator;
    private MinValidator minValidator;
    private LinkedList<Validator<Field, Object>> validators;

    public AnnotationValidator(){
        this.validators = new LinkedList<>();
        this.validators.add(new NonNullValidator());
        this.validators.add(new MinValidator());
        this.validators.add(new MaxValidator());
        this.validators.add(new MaxLengthValidator());
    }

    /**
     * Метод проверяет корректность значения для переданного поля в соответствии с имеющимися у поля аннотациями.
     * @param field - {@link Field}
     * @param value - значение, корректность которого необходимо проверить.
     * @return boolean
     */
    @Override
    public boolean isValid(Field field, Object value){
        for (Validator validator: validators){
            if (!validator.isValid(field, value)){
                return false;
            }
        }
        return true;
    }
}
