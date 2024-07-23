package controller.commands;

import commonModule.commands.CommandType;
import controller.CommandStorage;
import view.output.OutputUnit;

import java.net.SocketException;

public class CommandHistory extends AbstractCommand{
    private CommandStorage commandStorage;
    public CommandHistory(OutputUnit output, CommandStorage storage) throws SocketException {
        super(CommandType.HISTORY, output);
        this.commandStorage = storage;
    }

    @Override
    public Boolean execute(String[] command){
        if (command.length>1){
            this.output.writeln("The wrong number of elements was entered to execute the command" + this.getCommandType().toString() + "(" + (command.length-1) + " вместо 0)");
            return false;
        }else{
            this.output.writeln((String) this.commandStorage.show().getElement());
            return true;
        }
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " : output the last " + this.commandStorage.getCapacity() + " commands (without their arguments)";
    }
}
