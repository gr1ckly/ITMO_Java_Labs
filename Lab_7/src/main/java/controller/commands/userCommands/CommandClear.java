package controller.commands.userCommands;

import commonModule.utils.Response;
import model.CollectionStorage;
import commonModule.requests.Request;
import commonModule.commands.CommandType;

/**
 * Класс-наследник от {@link AbstractCommandWithDataBase}, задающий алгоритм выполнения команды clear.
 */
public class CommandClear extends AbstractCommandWithDataBase<Request, CollectionStorage>{
    public CommandClear(CollectionStorage storage){
        super(storage);
        this.commandType = CommandType.CLEAR;
    }

    /**
     *
     * @param request {@link Request} - метод получает {@link Request} с заполненной массивом {@link String} введенных пользователем данных и определяет алгоритм выполнения команды clear.
     * @return {@link Boolean}, обозначающий началось ли выполнение команды
     */
    @Override
    public Response<String> execute(Request request){
        return this.storage.clear(request.getUserName());
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " : очистить коллекцию";
    }
}
