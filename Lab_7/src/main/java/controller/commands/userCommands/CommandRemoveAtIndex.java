package controller.commands.userCommands;

import commonModule.utils.Response;
import model.CollectionStorage;
import commonModule.requests.Request;
import commonModule.commands.CommandType;

/**
 * Класс-наследник от {@link AbstractCommandWithDataBase}, задающий алгоритм выполнения команды remove_at.
 */
public class CommandRemoveAtIndex extends AbstractCommandWithDataBase<Request, CollectionStorage>{
    public CommandRemoveAtIndex(CollectionStorage storage){
        super(storage);
        this.commandType = CommandType.REMOVE_AT_INDEX;
    }

    /**
     *
     * @param request {@link Request} - метод получает {@link Request} с заполненной массивом {@link String} введенных пользователем данных и определяет алгоритм выполнения команды remove_at.
     * @return {@link Boolean}, обозначающий началось ли выполнение команды
     */
    @Override
    public Response<String> execute(Request request){
        return this.storage.remove((Integer) request.getElement(), request.getUserName());
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " index : удалить элемент, находящийся в заданной позиции коллекции (index)";
    }
}
