package controller.commands;

import commonModule.commands.Command;
import commonModule.commands.CommandType;
import view.output.OutputUnit;

import java.net.SocketException;
import java.util.EnumMap;

public class CommandHelp extends AbstractCommand{
    private EnumMap<CommandType, AbstractCommand> commands;
    public CommandHelp(OutputUnit output, EnumMap<CommandType, AbstractCommand> commands) throws SocketException {
        super(CommandType.HELP, output);
        this.commands = commands;
    }

    @Override
    public Boolean execute(String[] command){
        if (command.length > 1){
            this.output.writeln("The wrong number of elements was entered to execute the command" + this.getCommandType().toString() + "(" + (command.length-1) + " вместо 0)");
            return false;
        }else{
            if (this.commands == null | this.commands.size()==0){
                this.output.writeln(this.toString());
            }else{
                String message = "";
                for (CommandType commandType1: this.commands.keySet()){
                    message += this.commands.get(commandType1).toString() + "\n";
                }
                this.output.writeln(message);
            }
            return true;
        }
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " : output help for available commands";
    }
}
