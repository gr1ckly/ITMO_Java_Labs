package controller.commands;

import commonModule.commands.CommandType;
import commonModule.requests.NotAuthorizationException;
import controller.UserContainer;
import view.output.OutputUnit;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class CommandRemoveByID extends AbstractCommand{
    public CommandRemoveByID(OutputUnit output) throws SocketException {
        super(CommandType.REMOVE_BY_ID, output);
    }

    @Override
    public Boolean execute(String[] command) throws SocketException, SocketTimeoutException, IOException {
        if(command.length != 2){
            this.output.writeln("The wrong number of elements was entered to execute the command" + this.getCommandType().toString() + "(" + (command.length-1) + " вместо 1)");
            return false;
        }else{
            try{
                this.builder.setCommandType(CommandType.REMOVE_BY_ID);
                this.builder.setElement(Long.parseLong(command[1]));
                this.builder.setUserName(UserContainer.getUser().getUserName());
                try {
                    this.output.writeln(this.connector.getResponceFromServer(this.builder.build()).getElement());
                } catch (ClassNotFoundException e) {
                    this.output.writeln("The server response could not be recognized.");
                    return false;
                } catch (NotAuthorizationException e){
                    this.output.writeln(e.getMessage());
                }
            }catch (IllegalArgumentException e){
                this.output.writeln("The ID must be greater than 0 (less than " +Integer.MAX_VALUE+ ").");
            }
            return true;
        }
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " id : удалить элемент из коллекции по его id";
    }
}
