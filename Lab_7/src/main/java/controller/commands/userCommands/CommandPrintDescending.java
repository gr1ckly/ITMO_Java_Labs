package controller.commands.userCommands;

import commonModule.utils.Response;
import model.CollectionStorage;
import commonModule.requests.Request;
import commonModule.commands.CommandType;

/**
 * Класс-наследник от {@link AbstractCommandWithDataBase}, задающий алгоритм выполнения команды print_descending.
 */
public class CommandPrintDescending extends AbstractCommandWithDataBase<Request, CollectionStorage>{
    public CommandPrintDescending(CollectionStorage storage){
        super(storage);
        this.commandType = CommandType.PRINT_DESCENDING;
    }

    /**
     *
     * @param request {@link Request} - метод получает {@link Request} с заполненной массивом {@link String} введенных пользователем данных и определяет алгоритм выполнения команды print_descending.
     * @return {@link Boolean}, обозначающий началось ли выполнение команды
     */
    @Override
    public Response<String> execute(Request request){
        return this.storage.printDescending();
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " : вывести элементы коллекции в порядке убывания";
    }
}
