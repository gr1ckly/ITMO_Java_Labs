package controller.commands.userCommands;

import commonModule.utils.Response;
import model.CollectionStorage;
import commonModule.requests.Request;
import commonModule.commands.CommandType;

/**
 * Класс-наследник от {@link AbstractCommandWithDataBase}, задающий алгоритм выполнения команды remove_by_id.
 */
public class CommandRemoveByID extends AbstractCommandWithDataBase<Request, CollectionStorage>{
    public CommandRemoveByID(CollectionStorage storage){
        super(storage);
        this.commandType = CommandType.REMOVE_BY_ID;
    }

    /**
     *
     * @param request {@link Request} - метод получает {@link Request} с заполненной массивом {@link String} введенных пользователем данных и определяет алгоритм выполнения команды remove_by_id.
     * @return {@link Boolean}, обозначающий началось ли выполнение команды
     */
    @Override
    public Response<String> execute(Request request){
        return this.storage.removeByID((Long) request.getElement(), request.getUserName());
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " id : удалить элемент из коллекции по его id";
    }
}
