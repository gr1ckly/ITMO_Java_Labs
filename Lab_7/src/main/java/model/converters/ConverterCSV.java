package model.converters;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;

/**
 * Класс-наследник {@link Converter}, содержащий все необходимые методы для преобразования объектов в csv формат.
 */
public class ConverterCSV implements Converter<String, Object> {
    private String separator;

    /**
     *
     * @param separator - разделитель в файле.
     */
    public ConverterCSV(String separator){
        this.separator = separator;
    }

    /**
     * Метод, реализующий получения заголовка для таблицы.
     * @param Tclass - {@link Class}, для объектов которого необхождимо получить заголовок.
     * @return Заголовок в формате {@link String}.
     */
    public String getCSVTitle(Class Tclass){
        String message = "";
        for (Field field: Tclass.getDeclaredFields()){
            if (field.getType().isAssignableFrom(ZonedDateTime.class) | field.getType().isAssignableFrom(Integer.class) | field.getType().isAssignableFrom(Boolean.class) | field.getType().isAssignableFrom(Long.class) | field.getType().isAssignableFrom(String.class) | field.getType().isAssignableFrom(Byte.class) | field.getType().isAssignableFrom(Short.class) | field.getType().isAssignableFrom(Double.class) | field.getType().isAssignableFrom(Float.class) | field.getType().isAssignableFrom(Character.class) | field.getType().isPrimitive() | Enum.class.isAssignableFrom(field.getType())){
                message += Tclass.getName() + "'s " + field.getName() + this.separator;
            }else{
                message += Tclass.getName() + "'s " + field.getName() + this.separator + this.getCSVTitle(field.getType());
            }
        }
        return message;
    }

    public String getSeparator() {
        return separator;
    }

    /**
     * Метод, реализующий получение представления объекта в формате csv;
     * @param element - осходный объект
     * @return CSV-представление объекта в формате {@link String}
     */
    @Override
    public String convert(Object element) {
        String message = "";
        for (Field field: element.getClass().getDeclaredFields()){
            if (field.getType().isAssignableFrom(ZonedDateTime.class) | field.getType().isAssignableFrom(Integer.class) | field.getType().isAssignableFrom(Boolean.class) | field.getType().isAssignableFrom(Long.class) | field.getType().isAssignableFrom(String.class) | field.getType().isAssignableFrom(Byte.class) | field.getType().isAssignableFrom(Short.class) | field.getType().isAssignableFrom(Double.class) | field.getType().isAssignableFrom(Float.class) | field.getType().isAssignableFrom(Character.class) | field.getType().isPrimitive() | Enum.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                try {
                    var value = field.get(element);
                    if (value !=null){
                        message += value.toString().replace(this.separator, "/" + this.separator) + this.separator;
                    }else{
                        message += this.separator;
                    }
                }catch (IllegalAccessException e){
                    message += this.separator;
                }
                field.setAccessible(false);
            }else{
                field.setAccessible(true);
                try{
                    var value = field.get(element);
                    if (value!=null){
                        message += field.getName() + this.separator + this.convert(value);
                    }else{
                        for (int i=0; i<=this.getCSVTitle(field.getType()).split(this.separator).length; i++){
                            message += separator;
                        }
                    }
                }catch (IllegalAccessException e){
                    for (int i=0; i<=this.getCSVTitle(field.getType()).split(this.separator).length; i++){
                        message += separator;
                    }
                }
                field.setAccessible(false);
            }
        }
        return message;
    }
}
