package controller.commands.userCommands;

import commonModule.utils.Response;
import commonModule.collectionElements.MeleeWeapon;
import model.CollectionStorage;
import commonModule.requests.Request;
import commonModule.commands.CommandType;

/**
 * Класс-наследник от {@link AbstractCommandWithDataBase}, задающий алгоритм выполнения команды filter_greater_than_melee_weapon.
 */
public class CommandFilterGreaterThanMeleeWeapon extends AbstractCommandWithDataBase<Request, CollectionStorage>{
    public CommandFilterGreaterThanMeleeWeapon(CollectionStorage storage){
        super(storage);
        this.commandType = CommandType.FILTER_GREATER_THAN_MELEE_WEAPON;
    }

    /**
     *
     * @param request {@link Request} - метод получает {@link Request} с заполненной массивом {@link String} введенных пользователем данных и определяет алгоритм выполнения команды filter_greater_than_melee_weapon.
     * @return {@link Boolean}, обозначающий началось ли выполнение команды
     */
    @Override
    public Response<String> execute(Request request){
        return this.storage.filterGreaterThanMeleeWeapon((MeleeWeapon) request.getElement());
    }

    @Override
    public String toString(){
        String message = "";
        for (MeleeWeapon meleeWeapon: MeleeWeapon.values()){
            message += meleeWeapon.toString() + " ";
        }
        return this.commandType.getName() + " meleeWeapon : вывести элементы, значение поля meleeWeapon которых больше заданного ( meleeWeapon: " + message + ")";
    }
}
