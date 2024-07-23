package controller.commands.userCommands;

import commonModule.collectionElements.SpaceMarine;
import commonModule.utils.Response;
import commonModule.collectionElements.interfaces.IHaveInputData;
import model.CollectionStorage;
import commonModule.requests.Request;
import commonModule.commands.CommandType;

/**
 * Класс-наследник от {@link AbstractCommandWithDataBase}, задающий алгоритм выполнения команды add.
 */
public class CommandAdd extends AbstractCommandWithDataBase<Request, CollectionStorage>{

    public CommandAdd(CollectionStorage storage){
        super(storage);
        this.commandType = CommandType.ADD;
    }

    /**
     *
     * @param request {@link Request} - метод получает {@link Request} с заполненной массивом {@link String} введенных пользователем данных и определяет алгоритм выполнения команды add.
     * @return {@link Boolean}, обозначающий началось ли выполнение команды
     */
    @Override
    public Response<String> execute(Request request){
        return this.storage.add((SpaceMarine) request.getElement());
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " {element} : добавить новый элемент в коллекцию";
    }
}
