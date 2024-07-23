package commonModule.validation.annotations;

import commonModule.validation.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Класс-наследник {@link Validator}, проверяющий значение поля на соответствие аннотации {@link Min}.
 */
public class MinValidator implements Validator<Field, Object> {

    /**
     * Метод, осуществляющий валидацию аннотации {@link Min}.
     * @param field - заполняемое поле.
     * @param value - значение этого поля, требующее проверки.
     * @return - значение типа boolean с информацией о валидности данных для заполнения введенного поля.
     */
    @Override
    public boolean isValid(Field field, Object value){
        for (Annotation annotation: field.getAnnotations()) {
            if (annotation.annotationType().equals(Min.class)){
                Min minAnnotation = (Min) annotation;
                if (minAnnotation.isInclude()){
                    if (Double.parseDouble(value.toString()) < minAnnotation.minValue()){
                        return false;
                    }
                }else{
                    if (Double.parseDouble(value.toString()) <= minAnnotation.minValue()){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
