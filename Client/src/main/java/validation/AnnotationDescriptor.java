package validation;

import commonModule.validation.annotations.Max;
import commonModule.validation.annotations.MaxLength;
import commonModule.validation.annotations.Min;
import commonModule.validation.annotations.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Класс, отвечающий за создание описания поля.
 */
public class AnnotationDescriptor{
    /**
     * Метод создает описание поля на основе имеющихся у него аннотаций.
     * @param field - {@link Field}, для которого требуется описание.
     * @return {@link String} с описанием заданного поля.
     */
    public static String getDescription(Field field) {
        String description = "";
        for (Annotation annotation: field.getAnnotations()){
            if (annotation.annotationType().equals(NonNull.class)){
                description += AnnotationDescriptor.getNonNullDescription();
            } else if (annotation.annotationType().equals(Max.class)) {
                description += AnnotationDescriptor.getMaxDecription((Max) annotation);
            } else if (annotation.annotationType().equals(Min.class)) {
                description += AnnotationDescriptor.getMinDescription((Min) annotation);
            } else if (annotation.annotationType().equals(MaxLength.class)){
                description += AnnotationDescriptor.getMaxLengthDescription((MaxLength) annotation);
            }
        }
        return description.toLowerCase();
    }

    /**
     * Метод для получения описания для аннотации {@link NonNull}.
     * @return описание для аннотации {@link NonNull}
     */
    public static String getNonNullDescription(){
        return " The field to be filled in must not be null/an empty string";
    }

    /**
     * Метод для получения описания для аннотации {@link Max}
     * @param annotation аннотация типа {@link Max}
     * @return описание для конкретной аннотации {@link Max}
     */
    public static String getMaxDecription(Max annotation){
        String description = " less ";
        description += ((Max) annotation).maxValue();
        if (annotation.isInclude()) {
            description += " INCLUSIVE.";
        } else {
            description += " NOT INCLUSIVE.";
        }
        return description;
    }

    /**
     * Метод для получения описания для аннотации {@link Min}
     * @param annotation аннотация типа {@link Min}
     * @return описание для конкретной аннотации {@link Min}
     */
    public static String getMinDescription(Min annotation){
        String description = " greater ";
        description += annotation.minValue();
        if (annotation.isInclude()) {
            description += " INCLUSIVE.";
        } else {
            description += " NOT INCLUSIVE.";
        }
        return description;
    }

    public static String getMaxLengthDescription(MaxLength annotation){
        String description = " maximum length should be no more than ";
        description += annotation.maxLength();
        return description;
    }
}