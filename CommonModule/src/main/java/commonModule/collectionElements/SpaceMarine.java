package commonModule.collectionElements;

import commonModule.collectionElements.interfaces.*;
import commonModule.utils.GenID;
import commonModule.validation.annotations.Compare;
import commonModule.validation.annotations.InputData;
import commonModule.validation.annotations.Min;
import commonModule.validation.annotations.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.ZonedDateTime;

public class SpaceMarine implements IHaveID, IHaveInputData, IHaveMeleeWeapon, IHaveWeaponType, IHaveAutoFillable, IHaveName, Serializable {
    @Serial
    private static final int serialVersionUID = 234986777;
    @Compare
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автомати

    @NonNull
    @InputData
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NonNull
    @InputData
    private Coordinates coordinates; //Поле не может быть null

    @NonNull
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Min(minValue = 0.0, isInclude = false)
    @NonNull
    @InputData
    private long health; //Значение поля должно быть больше 0

    @NonNull
    @InputData
    private AstartesCategory category; //Поле не может быть null

    @InputData
    @Compare
    private Weapon weaponType; //Поле может быть null

    @InputData
    @Compare
    private MeleeWeapon meleeWeapon; //Поле может быть null

    @InputData
    private Chapter chapter; //Поле может быть null

    @NonNull
    private String ownerName;
    public SpaceMarine(){}

    @Override
    public void setAutomatically(){
        if (this.creationDate == null){
            this.creationDate = ZonedDateTime.now();
        }
    }

    public String getName() {
        return name;
    }

    public Chapter getChapter(){
        return this.chapter;
    }

    public String getChapterName() {
        return this.chapter.getName();
    }

    public long getChapterMarinesCount(){
        return this.chapter.getMarinesCount();
    }

    public int getCoordinatesX() {
        return this.coordinates.getX();
    }

    public float getCoordinatesY(){
        return this.coordinates.getY();
    }

    public Coordinates getCoordinates(){
        return this.coordinates;
    }

    @Override
    public long getId() {
        return id;
    }

    public long getHealth() {
        return health;
    }

    @Override
    public Weapon getWeaponType() {
        return weaponType;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    @Override
    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(AstartesCategory category) {
        this.category = category;
    }

    public void setCoordinatesX(int x){
        this.coordinates.setX(x);
    }

    public void setCoordinatesY(float y){
        this.coordinates.setY(y);
    }
    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public void setChapterName(String name){
        this.chapter.setName(name);
    }

    public void setChapterMarinesCount(long world){
        this.chapter.setMarinesCount(world);
    }

    public void setHealth(long health) {
        this.health = health;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public void setWeaponType(Weapon weaponType) {
        this.weaponType = weaponType;
    }
    public void setId(long id){
        this.id = id;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public boolean getAccess(String userName){
        if (userName.equals(this.ownerName)){
            return true;
        }
        return false;
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
        return "SpaceMarine " + message;    }

    @Override
    public int hashCode(){
        return Long.hashCode(this.id);
    }
}
