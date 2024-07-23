package controller.commands;

import commonModule.commands.CommandType;
import commonModule.requests.NotAuthorizationException;
import controller.UserContainer;
import view.output.OutputUnit;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class CommandPrintDescending extends AbstractCommand{
    public CommandPrintDescending(OutputUnit output) throws SocketException {
        super(CommandType.PRINT_DESCENDING, output);
    }

    @Override
    public Boolean execute(String[] command) throws SocketException, SocketTimeoutException, IOException {
        if (command.length != 1){
            this.output.writeln("The wrong number of elements was entered to execute the command" + this.getCommandType().toString() + "(" + (command.length-1) + " вместо 0)");
            return false;
        }else{
            this.builder.setCommandType(CommandType.PRINT_DESCENDING);
            this.builder.setUserName(UserContainer.getUser().getUserName());
            try {
                this.output.writeln(this.connector.getResponceFromServer(this.builder.build()).getElement());
                return true;
            } catch (ClassNotFoundException e) {
                this.output.writeln("The server response could not be recognized.");
                return false;
            } catch(NotAuthorizationException e){
                this.output.writeln(e.getMessage());
                return false;
            }
        }
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " : display the elements of the collection in descending order";
    }
}
