package commonModule.parsers;

import commonModule.utils.GenID;
import commonModule.collectionElements.interfaces.IHaveInputData;
import commonModule.validation.FormWithElementValidator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Класс-наследник {@link Parser}, используемый для создания элементов, помещаемых в коллекцию.
 * @param <T> - объект
 */
public class FormParser<T extends IHaveInputData> implements Parser<T, LinkedHashMap<Field, Object>, Class>{
    private Class Tclass;
    private FormWithElementValidator validator;
    public FormParser(){
        this.validator = new FormWithElementValidator();
    }

    /**
     * Метод, возвращающий экземпляр класса, переданного в метод, на основе заполненной {@link LinkedHashMap}
     * @param form - заполненная {@link LinkedHashMap} с данными для создания элемента.
     * @param tclass - {@link Class} элемента, который хотим создать.
     * @return <T> элемент, класс которого мы передали. Если форма невалидная, то возвращает null. Проверка формы на валидность происходит при помощи {@link FormWithElementValidator#isValid(HashMap, Class)}.
     */
    @Override
    public T parse(LinkedHashMap<Field, Object> form, Class tclass) {
        this.Tclass = tclass;
        T object = null;
        try {
            object = (T) this.Tclass.getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            return object;
        }
        if (this.validator.isValid(form, Tclass)) {
            Field[] fieldT = (Field[]) object.getClass().getDeclaredFields();
            for (Field field: form.keySet()){
                for (Field field1: fieldT){
                    if (field.getName().equals(field1.getName())){
                        field.setAccessible(true);
                        try {
                            var currentId = field.get(object);
                            field.set(object, form.get(field));
                            if (field.getName().toLowerCase().equals("id")){
                                GenID.addBusyId((Long) form.get(field));
                                GenID.removeID((Long) currentId);
                            }
                        }
                        catch(IllegalAccessException e){}
                        field.setAccessible(false);

                    }
                }
            }
            return object;
        }else {
            return null;
        }
    }
}
