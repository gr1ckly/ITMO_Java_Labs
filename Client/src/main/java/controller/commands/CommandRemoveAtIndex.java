package controller.commands;

import commonModule.commands.CommandType;
import commonModule.requests.NotAuthorizationException;
import controller.UserContainer;
import view.output.OutputUnit;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class CommandRemoveAtIndex extends AbstractCommand{
    public CommandRemoveAtIndex(OutputUnit output) throws SocketException {
        super(CommandType.REMOVE_AT_INDEX, output);
    }

    @Override
    public Boolean execute(String[] command) throws SocketException, SocketTimeoutException, IOException {
        if(command.length != 2){
            this.output.writeln("The wrong number of elements was entered to execute the command" + this.getCommandType().toString() + "(" + (command.length-1) + " вместо 1)");
            return false;
        }else{
            try{
                this.builder.setCommandType(CommandType.REMOVE_AT_INDEX);
                this.builder.setElement(Integer.parseInt(command[1]));
                this.builder.setUserName(UserContainer.getUser().getUserName());
                try {
                    this.output.writeln(this.connector.getResponceFromServer(this.builder.build()).getElement());
                } catch (ClassNotFoundException e) {
                    this.output.writeln("The server response could not be recognized.");
                    return false;
                }
            }catch (IllegalArgumentException e){
                this.output.writeln("The index must be greater than 0 (less than " +Integer.MAX_VALUE+ ").");
            } catch(NotAuthorizationException e){
                this.output.writeln(e.getMessage());
                return false;
            }
            return true;
        }
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " index : delete an item that is in the specified position of the collection (index)";
    }
}
