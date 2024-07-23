package model.parsers;

import commonModule.parsers.FormParser;
import commonModule.parsers.Parser;
import commonModule.validation.annotations.NonNull;
import server.OutputUnit;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;

/**
 * Класс-наслденик {@link Parser}, реализующий создание элемента по его csv-представлению.
 * @param <T>
 */
public class ParserCSV<T> implements Parser<T, LinkedHashMap<String, String>, Class> {
    public FormParser formParser;
    private String separator;
    private OutputUnit output;
    public ParserCSV(String separator, OutputUnit output){
        this.formParser = new FormParser();
        this.separator = separator;
        this.output = output;
    }

    /**
     * Метод создает элемент по его csv-представлению.
     * @param csvMap - {@link LinkedHashMap} c ключами - названиями столбцов в таблице и значениями - соответствующими значения полей объекта.
     * @param Tclass - {@link Class}, объект которого мы хотим получить.
     * @return Объект, созданный по его csv-представлению.
     */
    @Override
    public T parse(LinkedHashMap<String, String> csvMap, Class Tclass) {
        T element = null;
        try {
            element = (T) Tclass.getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            return element;
        }
        LinkedHashMap<Field, Object> form = new LinkedHashMap<>();
        for (Field fieldForm: Tclass.getDeclaredFields()){
            form.put(fieldForm, null);
        }
        for (Field field: form.keySet()){
            if (field.getType().isAssignableFrom(ZonedDateTime.class) | field.getType().isAssignableFrom(Integer.class) | field.getType().isAssignableFrom(Boolean.class) | field.getType().isAssignableFrom(Long.class) | field.getType().isAssignableFrom(String.class) | field.getType().isAssignableFrom(Byte.class) | field.getType().isAssignableFrom(Short.class) | field.getType().isAssignableFrom(Double.class) | field.getType().isAssignableFrom(Float.class) | field.getType().isAssignableFrom(Character.class) | field.getType().isPrimitive() | Enum.class.isAssignableFrom(field.getType())) {
                var value = csvMap.get(Tclass.getName() + "'s " + field.getName());
                if (value != null | value != ""){
                    form = this.putInMap(form, field, value);
                }
            }else{
                var value = csvMap.get(Tclass.getName() + "'s " + field.getName());
                if (value !=null && value.equals(field.getName())){
                    LinkedHashMap<String, String> includeForm = new LinkedHashMap<>();
                    for (String line: csvMap.keySet()){
                        if (line.contains(field.getType().getName() + "'s ")){
                            includeForm.put(line, csvMap.get(line));
                        }
                    }
                    form.put(field, this.parse(includeForm, field.getType()));
                }
            }
        }
        return (T) this.formParser.parse(form, Tclass);
    }

    public LinkedHashMap<Field, Object> putInMap(LinkedHashMap<Field, Object> form1, Field field, String value){
        if (form1.get(field) == null && value != null){
            try {
                if (field.getType().equals(Integer.class) | field.getType().equals(int.class)) {
                    form1.put(field, Integer.parseInt(value));
                } else if (field.getType().equals(Short.class) | field.getType().equals(short.class)) {
                    form1.put(field, Short.parseShort(value));
                } else if (field.getType().equals(Double.class) | field.getType().equals(double.class)) {
                    form1.put(field, Double.parseDouble(value));
                } else if (field.getType().equals(Float.class) | field.getType().equals(float.class)) {
                    form1.put(field, Float.parseFloat(value));
                } else if (field.getType().equals(Long.class) | field.getType().equals(long.class)) {
                    form1.put(field, Long.parseLong(value));
                } else if (field.getType().equals(Boolean.class) | field.getType().equals(boolean.class)) {
                    form1.put(field, Boolean.parseBoolean(value));
                } else if (field.getType().equals(Byte.class) | field.getType().equals(byte.class)) {
                    form1.put(field, Byte.parseByte(value));
                } else if (Enum.class.isAssignableFrom(field.getType())) {
                    form1.put(field, Enum.valueOf((Class<? extends Enum>) field.getType(), value));
                } else if (ZonedDateTime.class.isAssignableFrom(field.getType())){
                    form1.put(field, ZonedDateTime.parse(value.replace("\n", "")));
                }
                else{
                    form1.put(field, value.replace("\n", ""));
                }
            } catch (IllegalArgumentException | DateTimeParseException e) {
                if (field.getAnnotation(NonNull.class) != null) {
                    this.output.writeln("Некорректный формат данных в csv-файле для " + field.getType().getName() + " : - \"" + value + "\"");
                }
            }
        }
        return form1;
    }
}
