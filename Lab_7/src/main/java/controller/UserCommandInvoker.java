package controller;
import commonModule.commands.Invoker;
import commonModule.utils.Response;
import controller.commands.userCommands.*;
import model.CollectionStorage;
import server.InputUnit;
import server.OutputUnit;
import commonModule.requests.Request;
import commonModule.commands.CommandType;

import java.util.EnumMap;

/**
 * Класс, который осуществляет управление командами пользователя.
 */
public class UserCommandInvoker implements Invoker<Request, Response> {
    private EnumMap<CommandType, AbstractCommand> commandFetch;
    public UserCommandInvoker(EnumMap<CommandType, AbstractCommand> commandFetch){
        this.commandFetch = commandFetch;
    }

    /**
     *
     * @param request {@link Request} - объект, содержащий информацию о типе команды и данные, необходимые для ее выполнения.
     */
    @Override
    public Response<String> execute(Request request){
        return this.commandFetch.get(request.getCommandType()).execute(request);
    }
}
