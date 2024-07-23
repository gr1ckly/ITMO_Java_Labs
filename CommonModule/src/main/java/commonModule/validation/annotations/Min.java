package commonModule.validation.annotations;

import java.lang.annotation.*;

/**
 * Аннотация, с помощью которой можно обозначить минимальное значение вводимого поля. Можно ставить только над полями числового типа. Используется в {@link AnnotationDescriptor} и {@link AnnotationValidator}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Min {
    double minValue();
    /**
     * Определяет входит ли указанное значение в диапазон.
     * @return boolean
     */
    boolean isInclude();
}
