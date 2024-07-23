package commonModule.collectionElements;

import commonModule.collectionElements.interfaces.IHaveInputData;
import commonModule.validation.annotations.InputData;
import commonModule.validation.annotations.Max;
import commonModule.validation.annotations.Min;
import commonModule.validation.annotations.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Chapter implements IHaveInputData, Serializable {
    @Serial
    public static final int serialVersionUID = 945876;
    @InputData
    @NonNull
    private String name; //Поле не может быть null, Строка не может быть пустой
    @InputData
    @NonNull
    @Max(maxValue = 1000, isInclude = true)
    @Min(minValue = 0, isInclude = false)
    private long marinesCount; //Значение поля должно быть больше 0, Максимальное значение поля: 1000
    public Chapter(){}

    public Chapter(String name){
        this.name = name;
    }

    public Chapter(String name, long marinesCount){
        this.name = name;
        this. marinesCount = marinesCount;
    }

    public String getName() {
        return name;
    }

    public long getMarinesCount() {
        return this.marinesCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarinesCount(long world) {
        this.marinesCount = marinesCount;
    }

    @Override
    public String toString(){
        String message = "";
        for (Field field: this.getClass().getDeclaredFields()){
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    if (field.get(this) != null) {
                        message += field.getName() + "=" + field.get(this).toString() + "\n";
                    } else {
                        message += field.getName() + "=null\n";
                    }
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {}
            }
        }
        return "Chapter " + message;
    }

    @Override
    public boolean equals(Object otherObject){
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (this.getClass() != otherObject) return false;
        Chapter otherChapter = (Chapter) otherObject;
        return this.name==otherChapter.getName() && this.marinesCount==otherChapter.getMarinesCount();
    }
}