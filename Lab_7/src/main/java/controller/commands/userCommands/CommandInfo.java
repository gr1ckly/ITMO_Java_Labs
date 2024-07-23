package controller.commands.userCommands;

import commonModule.utils.Response;
import model.CollectionStorage;
import commonModule.requests.Request;
import commonModule.commands.CommandType;

/**
 * Класс-наследник от {@link AbstractCommandWithDataBase}, задающий алгоритм выполнения команды info.
 */
public class CommandInfo extends AbstractCommandWithDataBase<Request, CollectionStorage>{
    public CommandInfo(CollectionStorage storage){
        super(storage);
        this.commandType = CommandType.INFO;
    }

    /**
     *
     * @param request {@link Request} - метод получает {@link Request} с заполненной массивом {@link String} введенных пользователем данных и определяет алгоритм выполнения команды info.
     * @return {@link Boolean}, обозначающий началось ли выполнение команды
     */
    @Override
    public Response<String> execute(Request request){
        return new Response<>(this.storage.toString());
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
