package commonModule.utils;

import java.util.Stack;
import java.util.TreeSet;

/**
 * Класс, включающий в себя метода для работы с id.
 */
public class GenID {
    private static Stack<Long> idStorage = new Stack<Long>();
    private static long count = 0;
    private static TreeSet<Long> busyID = new TreeSet<>();

    /**
     * Метод сохраняет id, который занят.
     * @param id
     */
    public static void addBusyId(Long id){
        busyID.add(id);
    }

    /**
     * Метод позволяет получить уникальный id для элемента.
     * @return id в формате long
     */
    public static long getId(){
        if (idStorage.isEmpty()){
            count += 1;
            for (Long id: busyID) {
                if (count == id){
                    count += 1;
                }
            }
            GenID.addBusyId(count);
            return count;
        }
        else{
            return idStorage.pop();
        }
    }

    /**
     * Метод позволяет удалять освобожденные id из перечня занятых
     * @param id - освобожденный id.
     * @return значение типа boolean, обозначающее удалось ли выполнить действие.
     */
    public static boolean removeID(Long id){
        return busyID.remove(id);
    }

    /**
     * Метод возвращает {@link TreeSet} с занятыми значениями id.
     * @return {@link TreeSet} с занятыми id.
     */
    public static TreeSet<Long> getBusyID(){
        return GenID.busyID;
    }

    /**
     * Метод позволяет добавлять освободившиеся id.
     * @param id - освободившийся id.
     */
    public static void addId(long id){
        idStorage.push(id);
    }
}
