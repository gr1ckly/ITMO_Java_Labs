package commonModule.validation.annotations;

import commonModule.validation.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class MaxLengthValidator implements Validator<Field, Object> {

    /**
     * Метод, осуществляющий валидацию аннотации {@link MaxLength}.
     * @param field - заполняемое поле.
     * @param value - значение этого поля, требующее проверки.
     * @return - значение типа boolean с информацией о валидности данных для заполнения введенного поля.
     */
    @Override
    public boolean isValid(Field field, Object value){
        for (Annotation annotation: field.getAnnotations()){
            if (annotation.annotationType().equals(MaxLength.class)){
                MaxLength maxLengthAnnotation = (MaxLength) annotation;
                if (maxLengthAnnotation.maxLength() >= value.toString().length()) {
                    return true;
                }else{
                    return false;
                }
            }
        }
        return true;
    }
}

