package commonModule.validation;

import commonModule.collectionElements.interfaces.IHaveInputData;
import commonModule.validation.annotations.AnnotationValidator;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Класс-наследник {@link Validator}, используется для проверки формы на валидность для создания элемента.
 */
public class FormWithElementValidator implements Validator<HashMap<Field, Object>, Class> {
    private AnnotationValidator annotationValidator;
    public FormWithElementValidator(){
        this.annotationValidator = new AnnotationValidator();
    }

    /**
     * Метод проверяет валидность заполнения полей при помощи аннотаций, принадлежащих им.
     * @param form {@link java.util.LinkedHashMap} - заполненная форма.
     * @param object - объект {@link Class}, ззначения полей для которого мы хотим проверить на валидность.
     * @return boolean
     */
    @Override
    public boolean isValid(HashMap<Field, Object> form, Class object) {
        if (form == null){
            return false;
        }else {
            int countField = 0;
            boolean isCorrectValue = true;
            Object[] formFields = form.keySet().toArray();
            for (Object field : IHaveInputData.getInputData(object).keySet().toArray()) {
                for (Object field1 : formFields) {
                    if (((Field) field).getName().equals(((Field) field1).getName())) {
                        countField += 1;
                    }
                    if (!this.annotationValidator.isValid((Field) field1, form.get((Field) field1))) {
                        return false;
                    }
                }
            }
            if (countField == IHaveInputData.getInputData(object).keySet().toArray().length) {
                return true;
            } else {
                return false;
            }
        }
    }
}
