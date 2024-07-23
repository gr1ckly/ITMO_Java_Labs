package commonModule.validation.annotations;

import commonModule.validation.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Класс-наследник {@link Validator}, проверяющий значение поля на соответствие аннотации {@link Max}.
 */
public class MaxValidator implements Validator<Field, Object> {

    /**
     * Метод, осуществляющий валидацию аннотации {@link Max}.
     * @param field - заполняемое поле.
     * @param value - значение этого поля, требующее проверки.
     * @return - значение типа boolean с информацией о валидности данных для заполнения введенного поля.
     */
    @Override
    public boolean isValid(Field field, Object value){
        for (Annotation annotation: field.getAnnotations()){
            if (annotation.annotationType().equals(Max.class)){
                Max maxAnnotation = (Max) annotation;
                if (maxAnnotation.isInclude()){
                    if (Double.parseDouble(value.toString()) > maxAnnotation.maxValue()){
                        return false;
                    }
                }else{
                    if (Double.parseDouble(value.toString()) >= maxAnnotation.maxValue()){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
