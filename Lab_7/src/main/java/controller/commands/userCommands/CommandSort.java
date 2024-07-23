package controller.commands.userCommands;

import commonModule.utils.Response;
import model.CollectionStorage;
import commonModule.requests.Request;
import commonModule.commands.CommandType;

/**
 * Класс-наследник от {@link AbstractCommandWithDataBase}, задающий алгоритм выполнения команды sort.
 */
public class CommandSort extends AbstractCommandWithDataBase<Request, CollectionStorage>{
    public CommandSort(CollectionStorage storage){
        super(storage);
        this.commandType = CommandType.SORT;
    }

    /**
     *
     * @param request {@link Request} - метод получает {@link Request} с заполненной массивом {@link String} введенных пользователем данных и определяет алгоритм выполнения команды sort.
     * @return {@link Boolean}, обозначающий началось ли выполнение команды
     */
    @Override
    public Response<String> execute(Request request){
        return this.storage.sort();
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " : отсортировать коллекцию в естественном порядке";
    }
}
