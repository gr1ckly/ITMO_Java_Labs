package controller.commands.userCommands;

import commonModule.utils.Response;
import model.CollectionStorage;
import commonModule.requests.Request;
import commonModule.commands.CommandType;

/**
 * Класс-наследник от {@link AbstractCommandWithDataBase}, задающий алгоритм выполнения команды show.
 */
public class CommandShow extends AbstractCommandWithDataBase<Request, CollectionStorage>{
    public CommandShow(CollectionStorage storage){
        super(storage);
        this.commandType = CommandType.SHOW;
    }

    /**
     *
     * @param request {@link Request} - метод получает {@link Request} с заполненной массивом {@link String} введенных пользователем данных и определяет алгоритм выполнения команды show.
     * @return {@link Boolean}, обозначающий началось ли выполнение команды
     */
    @Override
    public Response<String> execute(Request request){
        return this.storage.show();
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
