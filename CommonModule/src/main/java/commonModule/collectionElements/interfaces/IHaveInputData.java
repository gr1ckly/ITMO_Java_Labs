package commonModule.collectionElements.interfaces;

import commonModule.validation.annotations.InputData;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

/**
 * Интерфейс, определяющий, что объект имеет вводимые поля.
 */
public interface IHaveInputData {
    /**
     * Статический метод, позволяющий получить LinkedHashMap со всеми вводимыми полями переданного Class.
     * @param object Class - класс, для которого должна быть получена LinkedHashMap<Field, Object>.
     * @return LinkedHashMap<Field, Object>, в которой ключи - объекты Field, имеющие аннотацию {@link InputData}, для введенного Class.
     */
    public static LinkedHashMap<Field, Object>  getInputData(Class object){
        LinkedHashMap<Field, Object> inputData = new LinkedHashMap<>();
        for (Field field: object.getDeclaredFields()){
            if (field.getAnnotation(InputData.class) != null){
                inputData.put(field, null);
            }
        }
        return  inputData;
    }
}
