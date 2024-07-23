package commonModule.validation.annotations;

import commonModule.collectionElements.interfaces.IHaveInputData;

import java.lang.annotation.*;

/**
 * Аннотация для пометки полей, которые хранят вводимые данные. Используется в {@link IHaveInputData}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface InputData {
}
