package commonModule.validation.annotations;

import commonModule.validation.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Класс-наследник {@link Validator}, проверяющий значение поля на соответствие аннотации {@link NonNull}.
 */
public class NonNullValidator implements Validator<Field, Object> {

    /**
     * Метод, осуществляющий валидацию аннотации {@link NonNull}.
     * @param field - заполняемое поле.
     * @param value - значение этого поля, требующее проверки.
     * @return - значение типа boolean с информацией о валидности данных для заполнения введенного поля.
     */
    @Override
    public boolean isValid(Field field, Object value){
        for (Annotation annotation: field.getAnnotations()) {
            if (annotation.annotationType().equals(NonNull.class)) {
                if (value == null) {
                    return false;
                } else if (String.class.isAssignableFrom(value.getClass()) && value == "") {
                    return false;
                }
            }
        }
        return true;
    }
}
