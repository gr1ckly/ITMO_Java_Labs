package controller.commands;

import commonModule.commands.CommandType;
import controller.UserContainer;
import view.input.InputMode;
import view.input.InputUnit;
import view.output.OutputUnit;

import java.io.IOException;
import java.net.SocketException;

public class CommandExit extends AbstractCommand{
    private InputUnit input;
    public CommandExit(OutputUnit output, InputUnit input) throws SocketException {
        super(CommandType.EXIT, output);
        this.input = input;
    }

    @Override
    public Boolean execute(String[] command){
        if (command.length != 1){
            this.output.writeln("The wrong number of elements was entered to execute the command" + this.getCommandType().toString() + "(" + (command.length-1) + " вместо 0)");
            return false;
        } else {
            this.input.setMode(InputMode.STOP);
            return true;
        }
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " : complete the program.";
    }
}
