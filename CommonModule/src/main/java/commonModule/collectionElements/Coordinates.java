package commonModule.collectionElements;

import commonModule.collectionElements.interfaces.IHaveInputData;
import commonModule.validation.annotations.InputData;
import commonModule.validation.annotations.Min;
import commonModule.validation.annotations.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Coordinates implements IHaveInputData, Serializable {
    @Serial
    private static final int serialVersionUID = 876543456;
    @InputData
    @NonNull
    private Integer x; //Поле не может быть null
    @InputData
    @NonNull
    @Min(minValue = -873, isInclude = false)
    private Float y; //Значение поля должно быть больше -873, Поле не может быть null

    public Coordinates(){}

    public Coordinates(int x, float y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
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
        return "Coordinates " + message;
    }

    @Override
    public boolean equals(Object otherObject){
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (this.getClass() != otherObject.getClass()) return false;
        Coordinates otherCoordinates = (Coordinates) otherObject;
        return this.x==otherCoordinates.getX() && this.y==otherCoordinates.getY();
    }
}