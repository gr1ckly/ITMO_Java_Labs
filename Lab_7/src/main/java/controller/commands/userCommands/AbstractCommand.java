package controller.commands.userCommands;
import commonModule.utils.Response;
import commonModule.commands.Command;
import commonModule.commands.CommandType;
import commonModule.requests.AbstractRequest;

/**
 * @param <T> T extends {@link AbstractRequest}
 */
public abstract class AbstractCommand<T extends AbstractRequest> implements Command<T, Response<String>> {
    protected CommandType commandType;
    public AbstractCommand(){}

    public CommandType getCommandType() {
        return commandType;
    }

    public abstract Response<String> execute(T request);
}
