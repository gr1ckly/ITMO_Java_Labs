package view;

import commonModule.collectionElements.interfaces.IHaveInputData;
import commonModule.parsers.FormParser;
import validation.AnnotationDescriptor;
import commonModule.validation.annotations.*;
import view.input.InputMode;
import view.input.InputUnit;
import view.output.OutputUnit;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

/**
 * Класс, содержащий инструменты для заполнения формы вводы данных в виде {@link LinkedHashMap}
 */
public class FillerForm {
    private InputUnit input;
    private OutputUnit output;
    private AnnotationValidator annotationValidator;
    public FillerForm(InputUnit input, OutputUnit output, AnnotationValidator annotationValidator){
        this.input = input;
        this.output = output;
        this.annotationValidator = annotationValidator;
    }

    /**
     * Метод, отвечающий за непосредственное заполнение формы.
     * @param form - заполняемая форма в виде {@link LinkedHashMap}.
     * @param parser - объект типа {@link FormParser} для создания элементов.
     * @return {@link LinkedHashMap} - заполненная форма.
     */
    public LinkedHashMap<Field, Object> fillForm(LinkedHashMap<Field, Object> form, FormParser parser){
        if (form == null){
            return null;
        }
        boolean isValid;
        for (Field field: form.keySet()){
            isValid = false;
            while (!isValid) {
                if (input.getMode() == InputMode.CONSOLE) {
                    String description = "Write " + field.getName();
                    if (Enum.class.isAssignableFrom(field.getType())){
                        String message = "";
                        for (Object object: field.getType().getEnumConstants()){
                            message += " " + object.toString();
                        }
                        description += message;
                    }
                    description += ": ";
                    this.output.write(description.replace("()", ""));
                }
                if (!input.canRead()){
                    return form;
                }
                if (this.isPrimitive(field)) {
                    try {
                        String value = this.input.readln();
                        if (value == null){
                            return form;
                        }
                        value = value.trim();
                        try {
                            if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                                isValid = annotationValidator.isValid(field, value);
                                form.put(field, Integer.parseInt(value));
                            } else if (field.getType().equals(Short.class) || field.getType().equals(short.class)) {
                                isValid = annotationValidator.isValid(field, value);
                                form.put(field, Short.parseShort(value));
                            } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                                isValid = annotationValidator.isValid(field, value);
                                form.put(field, Double.parseDouble(value));
                            } else if (field.getType().equals(Float.class) || field.getType().equals(float.class)) {
                                isValid = annotationValidator.isValid(field, value);
                                form.put(field, Float.parseFloat(value));
                            } else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                                isValid = annotationValidator.isValid(field, value);
                                form.put(field, Long.parseLong(value));
                            } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                                isValid = annotationValidator.isValid(field, value);
                                form.put(field, Boolean.parseBoolean(value));
                            } else if (field.getType().equals(Byte.class) || field.getType().equals(byte.class)) {
                                isValid = annotationValidator.isValid(field, value);
                                form.put(field, Byte.parseByte(value));
                            } else if (Enum.class.isAssignableFrom(field.getType())) {
                                isValid = annotationValidator.isValid(field, value);
                                form.put(field, Enum.valueOf((Class<? extends Enum>) field.getType(), value.toUpperCase()));
                            } else {
                                isValid = annotationValidator.isValid(field, value);
                                form.put(field, value);
                            }
                            if (!isValid) {
                                this.output.writeln("Invalid field value " + field.getName() + " - \"" + value + "\". " + this.getDescription(field));
                            }
                        } catch (IllegalArgumentException e) {
                            if (value == "" && field.getAnnotation(NonNull.class)==null){
                                isValid = true;
                                form.put(field, null);
                            }else {
                                isValid = false;
                                this.output.writeln("Field value(" + field.getName() + ") has an incorrect format - \"" + value + "\". " + this.getDescription(field));
                            }
                        }
                        if (input.getMode() != InputMode.CONSOLE) {
                            isValid = true;
                        }
                    }catch (NoSuchElementException e){
                        return null;
                    }
                } else {
                    this.output.writeln("");
                    if (field.getAnnotation(NonNull.class) != null) {
                        LinkedHashMap<Field, Object> form1 = this.fillForm(IHaveInputData.getInputData(field.getType()), parser);
                        form.put(field, parser.parse(form1, field.getType()));
                        isValid = true;
                    }else{
                        this.output.write("If you want to enter the value of this field, enter any character: ");
                        String word = input.readln().trim();
                        if (word != ""){
                            LinkedHashMap<Field, Object> form1 = this.fillForm(IHaveInputData.getInputData(field.getType()), parser);
                            form.put(field, parser.parse(form1, field.getType()));
                        }
                        isValid = true;
                    }
                }
            }
        }
        return form;
    }

    /**
     * Метод, показывающий, что поле не является составным.
     * @param field
     * @return boolean
     */
    public boolean isPrimitive(Field field){
        if (field.getType().isAssignableFrom(ZonedDateTime.class) | field.getType().isAssignableFrom(Integer.class) | field.getType().isAssignableFrom(Boolean.class) | field.getType().isAssignableFrom(Long.class) | field.getType().isAssignableFrom(String.class) | field.getType().isAssignableFrom(Byte.class) | field.getType().isAssignableFrom(Short.class) | field.getType().isAssignableFrom(Double.class) | field.getType().isAssignableFrom(Float.class) | field.getType().isAssignableFrom(Character.class) | field.getType().isPrimitive() | Enum.class.isAssignableFrom(field.getType())){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Метод, получающий описание поля в зависимости от его типа и имеющихся у него аннотаций.
     * @param field
     * @return {@link String}
     */
    public String getDescription(Field field){
        String description = "";
        if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
            description += "The value of the field to be filled in must be an integer";
            if (field.getAnnotation(Min.class) != null){
                description += AnnotationDescriptor.getMinDescription(field.getAnnotation(Min.class));
            }else{
                description += " greater than " + Integer.MIN_VALUE;
            }
            if (field.getAnnotation(Max.class) != null){
                description += AnnotationDescriptor.getMaxDecription(field.getAnnotation(Max.class));
            }else{
                description += " less than " + Integer.MAX_VALUE;
            }
            if (field.getAnnotation(NonNull.class) != null){
                description += AnnotationDescriptor.getNonNullDescription();
            }
        } else if (field.getType().equals(Short.class) || field.getType().equals(short.class)) {
            description += "The value of the field to be filled in must be an integer";
            if (field.getAnnotation(Min.class) != null){
                description += AnnotationDescriptor.getMinDescription(field.getAnnotation(Min.class));
            }else{
                description += " greater than " + Short.MIN_VALUE;
            }
            if (field.getAnnotation(Max.class) != null){
                description += AnnotationDescriptor.getMaxDecription(field.getAnnotation(Max.class));
            }else{
                description += " less than " + Short.MAX_VALUE;
            }
            if (field.getAnnotation(NonNull.class) != null){
                description += AnnotationDescriptor.getNonNullDescription();
            }
        } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
            description += "The value of the field to be filled in must be a fractional number with a maximum of 15 characters after the dot ";
            if (field.getAnnotation(Min.class) != null){
                description += AnnotationDescriptor.getMinDescription(field.getAnnotation(Min.class));
            }else{
                description += " greater than -1.7976931348623157 * 10^(308) ";
            }
            if (field.getAnnotation(Max.class) != null){
                description += AnnotationDescriptor.getMaxDecription(field.getAnnotation(Max.class));
            }else{
                description += " less than 1.7976931348623157 * 10^(308) ";
            }
            if (field.getAnnotation(NonNull.class) != null){
                description += AnnotationDescriptor.getNonNullDescription();
            }
        } else if (field.getType().equals(Float.class) || field.getType().equals(float.class)) {
            description += "The value of the field to be filled in must be a fractional number with a maximum of 8 characters after the dot ";
            if (field.getAnnotation(Min.class) != null){
                description += AnnotationDescriptor.getMinDescription(field.getAnnotation(Min.class));
            }else{
                description += " greater than -3.4028235 * 10^(38)";
            }
            if (field.getAnnotation(Max.class) != null){
                description += AnnotationDescriptor.getMaxDecription(field.getAnnotation(Max.class));
            }else{
                description += " less than 3.4028235 * 10^(38)";
            }
            if (field.getAnnotation(NonNull.class) != null){
                description += AnnotationDescriptor.getNonNullDescription();
            }
        } else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
            description += "The value of the field to be filled in must be an integer ";
            if (field.getAnnotation(Min.class) != null){
                description += AnnotationDescriptor.getMinDescription(field.getAnnotation(Min.class));
            }else{
                description += " greater than " + Long.MIN_VALUE;
            }
            if (field.getAnnotation(Max.class) != null){
                description += AnnotationDescriptor.getMaxDecription(field.getAnnotation(Max.class));
            }else{
                description += " less than " + Long.MAX_VALUE;
            }
            if (field.getAnnotation(NonNull.class) != null){
                description += AnnotationDescriptor.getNonNullDescription();
            }
        } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
            description += "The value of the field to be filled in can take only 2 values - true/false ";
            if (field.getAnnotation(NonNull.class) != null){
                description += AnnotationDescriptor.getNonNullDescription();
            }
        } else if (field.getType().equals(Byte.class) || field.getType().equals(byte.class)) {
            description += "The value of the field to be filled in must be an integer ";
            if (field.getAnnotation(Min.class) != null){
                description += AnnotationDescriptor.getMinDescription(field.getAnnotation(Min.class));
            }else{
                description += " greater than " + Byte.MIN_VALUE;
            }
            if (field.getAnnotation(Max.class) != null){
                description += AnnotationDescriptor.getMaxDecription(field.getAnnotation(Max.class));
            }else{
                description += " less than " + Byte.MAX_VALUE;
            }
            if (field.getAnnotation(NonNull.class) != null){
                description += AnnotationDescriptor.getNonNullDescription();
            }
        }else if (Enum.class.isAssignableFrom(field.getType())){
            description += "The values of this field can only be as follows ";
            for (Object object:field.getType().getEnumConstants()){
                description += object.toString() + " ";
            }
            if (field.getAnnotation(NonNull.class) != null){
                description += AnnotationDescriptor.getNonNullDescription();
            }
        }else{
            description = "The value of the field must be a string ";
            if (field.getAnnotation(NonNull.class) != null){
                description += AnnotationDescriptor.getNonNullDescription();
            }
        }
        return description;
    }

}
