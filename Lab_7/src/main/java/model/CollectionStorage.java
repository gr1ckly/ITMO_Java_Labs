package model;

import commonModule.collectionElements.interfaces.*;
import commonModule.utils.GenID;
import commonModule.utils.Response;
import commonModule.collectionElements.*;
import commonModule.validation.ElementsComparator;
import model.converters.ConverterCSV;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validation.exceptions.CSVInvalidTitleException;
import validation.exceptions.CSVNotTitleException;
import validation.exceptions.InvalidFileNameException;
import model.readers.ReaderCSV;
import model.writers.WriterCSV;
import server.InputUnit;
import server.OutputUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс-наследник {@link Storage}, отвечающий за хранение элементов коллекции и реализацию команд.
 */

public class CollectionStorage implements Storage<Response, Integer, SpaceMarine, String>{
    private List<SpaceMarine> storage;
    private LocalDateTime creationDate;
    private ElementsComparator comparator;
    private SpaceMarineDBManager dbManager;


    public CollectionStorage(SpaceMarineDBManager dbManager){
        this.comparator = new ElementsComparator();
        this.creationDate = LocalDateTime.now();
        this.dbManager = dbManager;
        if (this.dbManager != null){
            try {
                this.storage = this.dbManager.loadCollection();
            } catch (SQLException e) {
            }
        }
        if (this.storage == null){
            this.storage = new LinkedList<>();
        }
        this.storage = (LinkedList<SpaceMarine>) this.storage.stream().sorted(this.comparator).collect(Collectors.toCollection(LinkedList::new));
        this.storage = Collections.synchronizedList(this.storage);
    }

    /**
     * Метод, реализующий выполнение команды sort. В качестве {@link Comparator} используется {@link ElementsComparator}
     * @return {@link Response} с ответом на попытку выполнения команды sort.
     */
    public synchronized Response<String> sort() {
        if (this.storage.toArray().length != 0) {
            try {
                this.storage = this.dbManager.loadCollection();
                this.storage = Collections.synchronizedList((List<SpaceMarine>) this.storage.stream().sorted(this.comparator).collect(Collectors.toCollection(LinkedList::new)));
                return new Response("The collection has been successfully sorted.");
            } catch (SQLException e) {
                return new Response<>("The collection could not be accessed.");
            }
        }else{
            return new Response<>("The collection is empty.");
        }
    }

    /**
     * Метод, реализующий выполнение команды add.
     * @param object - эелемент, который нужно добавить в коллекцию.
     * @return {@link Response} с ответом на попытку выполнения команды add.
     */
    public synchronized Response<String> add(SpaceMarine object){
        if (object != null) {
                object.setAutomatically();
                this.storage.add(object);
                this.sort();
            try {
                this.dbManager.add(object);
            } catch (SQLException e) {
            }
            return new Response<String>("The item has been successfully added to the collection.");
        }else{
            return new Response<String>("The element has invalid field values. It has not been added to the collection.");
        }
    }

    /**
     * Метод, реализующий выполнение команды remove.
     * @param id - id в формате {@link Long} элемента который необходимо удалить
     * @return {@link Response} с ответом на попытку выполнения команды remove.
     */

    public synchronized Response<String> removeByID(Long id, String userName) {
            for (SpaceMarine spaceMarine: this.storage){
                if (spaceMarine.getId() == id){
                    if (spaceMarine.getAccess(userName)){
                        try {
                            this.dbManager.remove(spaceMarine.getId());
                            this.storage.remove(spaceMarine);
                            return new Response<>("The item with id: " + id + " was succefully removed.");
                        } catch (SQLException e) {
                            return new Response<>("Failed to delete an item with an id: " + id);
                        }
                    }else{
                        return new Response<>("You do not have sufficient rights to manage this object.");
                    }
                }
            }
            return new Response("There is no element in the collection with id: " + id + ".");
    }

    @Override
    public synchronized Response<String> remove(Integer index, String userName){
            if (this.storage.toArray().length == 0) {
                return new Response("The collection is empty.");
            }
            if (this.storage.size() > index & index >= 0){
                try {
                    SpaceMarine spaceMarine = this.storage.get(index);
                    if (spaceMarine.getAccess(userName)) {
                        this.storage.remove(index);
                        this.dbManager.remove(spaceMarine.getId());
                    }else{
                        return new Response<>("You do not have sufficient rights to manage this item.");
                    }
                }catch(SQLException e){
                    return new Response<>("The item could not be deleted.");
                }
                return new Response<>("The item with index: " + index + " successfully deleted from the collection.");
            }else{
                return new Response<>("The item with index " + index + " does not exist in the collection.");
            }
    }

    /**
     * Метод, реализующий выполнение команды clear.
     * @return {@link Response} с ответом на попытку выполнения команды clear.
     */
    @Override
    public synchronized Response<String> clear(String userName) {
        if (this.storage.toArray().length==0){
            return new Response("The collection is empty.");
        }
        for (SpaceMarine spaceMarine: this.storage){
            if (spaceMarine.getAccess(userName)) {
                try {
                    this.dbManager.remove(spaceMarine.getId());
                    this.storage.remove(spaceMarine);
                } catch (SQLException e) {
                }
            }
        }
        return new Response("The collection has been successfully cleared.");
    }

    /**
     * Метод, реализующий выполнение команды show.
     * @return {@link Response} с ответом на попытку выполнения команды show.
     */
    public synchronized Response<String> show() {
        try {
            this.storage = this.dbManager.loadCollection();
            String message;
            if (this.storage.toArray().length != 0) {
                message = this.storage.stream().sorted(Comparator.comparing(SpaceMarine::getName)).map(SpaceMarine::toString).collect(Collectors.joining("\n")).toString();
            }else{
                message = "Collection is empty.";
            }
            return new Response(message);
        } catch (SQLException e) {
            return new Response<>("Collection is empty.");
        }
    }

    /**
     * Метод, реализующий выполнение команды update.
     * @param element - объект, с обновленными даннымию
     * @param id - id объекта, который необходимо обновить
     * @return {@link Response} с ответом на попытку выполнения команды update.
     */
    public synchronized Response<String> updateByID(SpaceMarine element, Long id, String userName) {
        if (element != null) {
            for (SpaceMarine object : storage) {
                if (object.getId() == id) {
                    if (object.getAccess(userName)) {
                        try {
                            this.storage.remove(object);
                            element.setId(id);
                            this.storage.add(element);
                            this.sort();
                            this.dbManager.update(id, element);
                            return new Response("The item with id: " + element.getId() + " was succefully updated.");
                        } catch (SQLException e) {
                        }
                        return new Response<>("Failed to update collection item with id: " + id);
                    }else{
                        return new Response<>("You do not have sufficient rights to manage this object.");
                    }
                }
            }
            return new Response("The item with id: " + element.getId() + " is not in the collection.");
        }else{
            return new Response("The element has invalid field values. It has not been added to the collection.");
        }
    }

    /**
     * Метод, реализующий выполнение команды min_by_weapon_type.
     * @return {@link Response} с ответом на попытку выполнения команды min_by_weapon_type.
     */
    public synchronized Response<String> minByWeaponType(){
        try {
            this.storage = this.dbManager.loadCollection();
            if (this.storage.toArray().length == 0){
                return new Response("The collection is empty.");
            }
            SpaceMarine element = null;
            for (SpaceMarine object: this.storage){
                if (object.getWeaponType() != null & object.getWeaponType().getValue() < element.getWeaponType().getValue()) {
                    element = object;
                }
            }
            return new Response(element.toString());
        } catch (SQLException e) {
            return new Response<>("The collection is empty.");
        }
    }

    /**
     * Метод, реализующий выполнение команды filter_greater_than_melee_weapon.
     * @param meleeWeapon - объект типа {@link MeleeWeapon}, с которым происходит сравнение.
     * @return {@link Response} с ответом на попытку выполнения команды filter_greater_than_melee_weapon.
     */
    public synchronized Response<String> filterGreaterThanMeleeWeapon(MeleeWeapon meleeWeapon){
        try {
            this.storage = this.dbManager.loadCollection();
            if (this.storage.toArray().length == 0){
                return new Response("The collection is empty.");
            }
            String message = "";
            for (SpaceMarine object: this.storage){
                if (object.getMeleeWeapon() != null) {
                    if (object.getMeleeWeapon().getValue() > meleeWeapon.getValue()) {
                        message += object.toString() + "\n";
                    }
                }
            }
            if (message.equals("")){
                return new Response("There are no items in the collection whose MeleeWeapon field value is greater than the one entered.");
            }
            return new Response(message);
        } catch (SQLException e) {
            return new Response<>("The collection is empty.");
        }
    }

    /**
     * Метод, реализующий выполнение команды print_descending.
     * @return {@link Response} с ответом на попытку выполнения команды print_descending.
     */
    public synchronized Response<String> printDescending(){
        try {
            this.storage = this.dbManager.loadCollection();
            if (this.storage.toArray().length == 0){
                return new Response("The collection is empty.");
            }
            String message = this.storage.stream().sorted(this.comparator).map(T -> T.toString()).collect(Collectors.joining("\n")).toString();
            System.out.println(message);
            return new Response(message);
        } catch (SQLException e) {
            return new Response<>("The collection is empty.");
        }
    }

    @Override
    public synchronized String toString(){
        return "Collection's type: LinkedHashSet\n" + "Element's type: SpaceMarine\nNumber of elements: " + this.storage.size() + "\nCreation date: " + this.creationDate.toString();
    }
}
