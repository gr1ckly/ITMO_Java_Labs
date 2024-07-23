package commonModule.validation;

import commonModule.collectionElements.interfaces.Valuable;
import commonModule.validation.annotations.Compare;
import commonModule.validation.annotations.NonNull;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Класс-наследник {@link Comparator}, сравнивающий элементы.
 * @param <T>
 */
public class ElementsComparator<T> implements Comparator<T> {

    /**
     * Метод возвращает результат сравнения объектов по полям, отмеченных аннотацией {@link Compare}
     * @param sp1 первый объект, который мы хотим сравнивать.
     * @param sp2 второй объект который мы хотим сравнивать.
     * @return значение типа int -результат сравнения.
     */
    @Override
    public int compare(T sp1, T sp2){
        double res = 0;
        if (sp1.getClass().equals(sp2.getClass())){
            LinkedList<Field> fields = new LinkedList<>();
            for (Field field: sp1.getClass().getDeclaredFields()){
                if (field.getAnnotation(Compare.class) != null && field.getAnnotation(NonNull.class) != null &&  (Valuable.class.isAssignableFrom(field.getType()) | field.getType().equals(Integer.class) | field.getType().equals(Long.class) | field.getType().equals(Float.class) | field.getType().equals(Double.class))){
                    field.setAccessible(true);
                    try {
                        if (field.getType().equals(Integer.class)) {
                            res += (int) field.get(sp1) - (int) field.get(sp2);
                        } else if (field.getType().equals(Float.class)) {
                            res += (float) field.get(sp1) - (float) field.get(sp2);
                        } else if (field.getType().equals(Long.class)) {
                            res += (double) ((long) field.get(sp1) - (long) field.get(sp2));
                        } else if (field.getType().equals(Double.class)) {
                            res += (double) field.get(sp1) - (double) field.get(sp2);
                        } else if (Valuable.class.isAssignableFrom(field.getType())){
                            res += ((Valuable) field.get(sp1)).getValue() - ((Valuable) field.get(sp2)).getValue();
                        }
                        res += 0;
                    }
                    catch(IllegalAccessException e){
                        res += 0;
                    }
                    field.setAccessible(false);
                }
            }
        }
        return (int) Math.round(res);
    }
}
