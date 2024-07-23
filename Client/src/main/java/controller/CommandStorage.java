package controller;

import commonModule.utils.Response;
import controller.commands.AbstractCommand;
import commonModule.commands.Command;
import controller.exceptions.PositiveValueException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Класс-наслденик {@link Storage}, использующийся для хранения истории выполненных команд.
 */
public class CommandStorage implements Storage<Response, AbstractCommand, AbstractCommand> {
    public Deque<AbstractCommand> storage;
    public int capacity;

    public CommandStorage(int capacity) throws PositiveValueException {
        if (capacity<1){
            this.capacity = -1;
            throw new PositiveValueException("Количество хранящихся команд должно быть строго положительным");
        }else{
            this.capacity = capacity;
            this.storage = new ArrayDeque<>(capacity);
        }
    }

    public CommandStorage(){
        this.storage = new LinkedList<>();
        this.capacity = -1;
    }

    @Override
    public Response add(AbstractCommand command) {
        if (this.capacity == -1){
            this.storage.addLast(command);
        }else{
            if (this.storage.size()<this.capacity){
                this.storage.addLast(command);
            }else{
                this.storage.removeFirst();
                this.storage.addLast(command);
            }
        }
        return new Response(true);
    }

    @Override
    public Response remove(AbstractCommand t) {
        for (Command command: this.storage){
            if (t.equals(command)){
                this.storage.remove(command);
                return new Response(true);
            }
        }
        return new Response(false);
    }

    @Override
    public Response clear() {
        this.storage.clear();
        return new Response(true);
    }

    public Deque<AbstractCommand> getStorage() {
        return storage;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public Response show(){
        if (this.storage.toArray().length == 0){
            return new Response("Не было вызвано ни одной команды.");
        }
        String message = "";
        for (AbstractCommand command: this.storage){
            message = command.getCommandType().getName() + "\n" + message;
        }
        return new Response(message);
    }
}
