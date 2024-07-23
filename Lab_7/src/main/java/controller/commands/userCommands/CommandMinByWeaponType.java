package controller.commands.userCommands;

import commonModule.utils.Response;
import model.CollectionStorage;
import commonModule.requests.Request;
import commonModule.commands.CommandType;

/**
 * Класс-наследник от {@link AbstractCommandWithDataBase}, задающий алгоритм выполнения команды min_by_weapon_type.
 */
public class CommandMinByWeaponType extends AbstractCommandWithDataBase<Request, CollectionStorage>{
    public CommandMinByWeaponType(CollectionStorage storage){
        super(storage);
        this.commandType = CommandType.MIN_BY_WEAPON_TYPE;
    }

    /**
     *
     * @param request {@link Request} - метод получает {@link Request} с заполненной массивом {@link String} введенных пользователем данных и определяет алгоритм выполнения команды min_by_weapon_type.
     * @return {@link Boolean}, обозначающий началось ли выполнение команды
     */
    @Override
    public Response<String> execute(Request request){
        return this.storage.minByWeaponType();
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " : вывести любой объект из коллекции, значение поля weaponType которого является минимальным";
    }
}
