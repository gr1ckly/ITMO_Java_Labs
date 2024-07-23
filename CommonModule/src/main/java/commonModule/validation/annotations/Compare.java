package commonModule.validation.annotations;

import commonModule.validation.ElementsComparator;

import java.lang.annotation.*;

/**
 * Аннотация для указания на поля, по которым необходимо сравнивать объекты. Будет работать только если указывается над полем типа имеющим числовой тип.
 * Проверяется в {@link ElementsComparator}.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Compare {
}
