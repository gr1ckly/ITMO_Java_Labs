package controller;

import commonModule.commands.CommandType;
import controller.commands.*;
import controller.exceptions.PositiveValueException;
import view.input.InputUnit;
import view.output.OutputUnit;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.EnumMap;

public class Invoker {
    private CommandHelp commandHelp;
    private CommandInfo commandInfo;
    private CommandRemoveByID commandRemoveByID;
    private CommandShow commandShow;
    private CommandClear commandClear;
    private CommandExecuteScript commandExecuteScript;
    private CommandExit commandExit;
    private CommandFilterGreaterThanMeleeWeapon commandFilterGreaterThanMeleeWeapon;
    private CommandHistory commandHistory;
    private CommandMinByWeaponType commandMinByWeaponType;
    private CommandPrintDescending commandPrintDescending;
    private CommandRemoveAtIndex commandRemoveAtIndex;
    private CommandSort commandSort;
    private CommandUpdateID commandUpdateID;
    private CommandStorage commandStorage;
    private CommandAdd commandAdd;
    private EnumMap<CommandType, AbstractCommand> commandFetch;
    private InputUnit inputUnit;
    private OutputUnit outputUnit;
    public Invoker(OutputUnit output, InputUnit input) throws SocketException {
        try {
            this.commandStorage = new CommandStorage(6);
        }catch(PositiveValueException e){}
        this.outputUnit = output;
        this.inputUnit = input;
        this.commandFetch = new EnumMap<>(CommandType.class);
        this.commandAdd = new CommandAdd(output, input);
        this.commandFetch.put(CommandType.ADD, this.commandAdd);
        this.commandHelp = new CommandHelp(output, this.commandFetch);
        this.commandFetch.put(CommandType.HELP, commandHelp);
        this.commandInfo = new CommandInfo(output);
        this.commandFetch.put(CommandType.INFO, this.commandInfo);
        this.commandShow = new CommandShow(output);
        this.commandFetch.put(CommandType.SHOW, this.commandShow);
        this.commandRemoveByID = new CommandRemoveByID(output);
        this.commandFetch.put(CommandType.REMOVE_BY_ID, this.commandRemoveByID);
        this.commandClear = new CommandClear(output);
        this.commandFetch.put(CommandType.CLEAR, this.commandClear);
        this.commandExecuteScript = new CommandExecuteScript(output, input);
        this.commandFetch.put(CommandType.EXECUTE_SCRIPT, this.commandExecuteScript);
        this.commandExit = new CommandExit(output, input);
        this.commandFetch.put(CommandType.EXIT, this.commandExit);
        this.commandFilterGreaterThanMeleeWeapon = new CommandFilterGreaterThanMeleeWeapon(output);
        this.commandFetch.put(CommandType.FILTER_GREATER_THAN_MELEE_WEAPON, this.commandFilterGreaterThanMeleeWeapon);
        this.commandHistory = new CommandHistory(output, this.commandStorage);
        this.commandFetch.put(CommandType.HISTORY, this.commandHistory);
        this.commandMinByWeaponType = new CommandMinByWeaponType(output);
        this.commandFetch.put(CommandType.MIN_BY_WEAPON_TYPE, this.commandMinByWeaponType);
        this.commandPrintDescending = new CommandPrintDescending(output);
        this.commandFetch.put(CommandType.PRINT_DESCENDING, this.commandPrintDescending);
        this.commandRemoveAtIndex= new CommandRemoveAtIndex(output);
        this.commandFetch.put(CommandType.REMOVE_AT_INDEX, this.commandRemoveAtIndex);
        this.commandSort= new CommandSort(output);
        this.commandFetch.put(CommandType.SORT, this.commandSort);
        this.commandUpdateID= new CommandUpdateID(output, input);
        this.commandFetch.put(CommandType.UPDATE_ID, this.commandUpdateID);
    }
    public void execute(CommandType commandType, String[] command) throws IOException {
        try {
            if ((Boolean) this.commandFetch.get(commandType).execute(command)){
                this.commandStorage.add(this.commandFetch.get(commandType));
            }
        } catch (SocketException | SocketTimeoutException e) {
            this.outputUnit.write("The server is temporarily unavailable. If you want to try to connect again, enter any character: ");
            String answer = this.inputUnit.readln().trim();
            if (answer == ""){
                this.inputUnit.stopInput();
            }else{
                this.outputUnit.writeln("Reconnect...");
                this.execute(commandType, command);
            }
        }
    }
}
