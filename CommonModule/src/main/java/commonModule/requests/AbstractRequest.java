package commonModule.requests;

import commonModule.commands.CommandType;

import java.io.Serial;
import java.io.Serializable;

/**
 * Абстрактный класс, используемый для создания на его основе объектов-запросов.
 */
public abstract class AbstractRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 2318490;
    protected CommandType commandType;
    protected String userName;
    public AbstractRequest(CommandType commandType, String userName) throws NotAuthorizationException {
        this.commandType = commandType;
        this.userName = userName;
        if (this.userName == null){
            throw new NotAuthorizationException("You haven't authorization.");
        }
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getUserName() {
        return userName;
    }
}
