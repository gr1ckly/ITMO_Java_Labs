package commonModule.validation.annotations;

import java.lang.annotation.*;

/**
 * Аннотация, которая указывает что поле не может иметь значение null (для {@link String} не может быть пустой строкой).
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NonNull{
}
