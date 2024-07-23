package controller.commands.userCommands;

import commonModule.commands.CommandType;
import commonModule.requests.Request;
import commonModule.utils.Response;
import commonModule.utils.User;
import model.UserDBManager;

import java.sql.SQLException;

public class CommandLogin extends AbstractCommand<Request<Boolean>>{
    private UserDBManager dbManager;
    public CommandLogin(UserDBManager dbManager){
        this.commandType = CommandType.LOGIN;
        this.dbManager = dbManager;
    }
    @Override
    public Response<Boolean> execute(Request request) {
        try {
            return new Response<>(this.dbManager.login((User) request.getElement()));
        } catch (SQLException e) {
            return new Response<>(false);
        }
    }
}
