package controller.commands.userCommands;

import commonModule.commands.CommandType;
import commonModule.requests.Request;
import commonModule.utils.Response;
import commonModule.utils.User;
import model.UserDBManager;

import java.sql.SQLException;

public class CommandAuthorization extends AbstractCommand<Request>{
    private UserDBManager dbManager;
    public CommandAuthorization(UserDBManager dbManager){
        this.commandType = CommandType.AUTHORIZATION;
        this.dbManager = dbManager;
    }
    @Override
    public Response execute(Request request){
        try {
            return new Response(this.dbManager.authorization((User) request.getElement()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new Response(false);
        }
    }
}
