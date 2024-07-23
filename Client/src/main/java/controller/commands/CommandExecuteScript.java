package controller.commands;

import commonModule.commands.CommandType;
import controller.UserContainer;
import controller.exceptions.RecursionException;
import view.input.InputMode;
import view.input.InputUnit;
import view.output.OutputUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.SocketException;
import java.util.Scanner;

public class CommandExecuteScript extends AbstractCommand{
    private InputUnit input;
    public CommandExecuteScript(OutputUnit output, InputUnit input) throws SocketException {
        super(CommandType.EXECUTE_SCRIPT, output);
        this.input = input;
    }

    @Override
    public Boolean execute(String[] command){
        if (command.length != 2){
            this.output.writeln("The wrong number of elements was entered to execute the command" + this.getCommandType().toString() + "(" + (command.length-1) + " вместо 1)");
            return false;
        }else{
            String path = command[1];
            try {
                try{
                    this.input.readFile(new File(path));
                    this.output.writeln("Start doing the script - " + path + ".");
                }catch (RecursionException e){
                    this.output.writeln(e.getMessage());
                    this.input.clear();
                    this.input.addInputDevice(new Scanner(System.in));
                    this.input.setMode(InputMode.CONSOLE);
                }
            } catch (FileNotFoundException e) {
                this.output.writeln("Can't read the script - " + path + ".");
            }
            return true;
        }
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " file_name : read and execute the script from the specified file.";
    }
}
