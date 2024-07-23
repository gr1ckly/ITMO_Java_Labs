package server;

import commonModule.collectionElements.SpaceMarine;
import commonModule.commands.CommandType;
import commonModule.utils.App;
import controller.ServerCommandInvoker;
import controller.UserCommandInvoker;
import controller.commands.serverCommands.AbstractServerCommand;
import controller.commands.serverCommands.CommandServerExit;
import controller.commands.userCommands.*;
import model.CollectionStorage;
import model.SpaceMarineDBManager;
import model.UserDBManager;
import server.network.Server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Scanner;

import static server.ServerMode.CONSOLE;

public class ServerApp implements App {
    private Server server;
    private CollectionStorage storage;
    private InputUnit inputUnit;
    private OutputUnit outputUnit;
    private ServerCommandInvoker serverCommandInvoker;
    private UserCommandInvoker userCommandInvoker;
    private CommandAdd commandAdd;
    private CommandInfo commandInfo;
    private CommandRemoveByID commandRemoveByID;
    private CommandShow commandShow;
    private CommandClear commandClear;
    private CommandFilterGreaterThanMeleeWeapon commandFilterGreaterThanMeleeWeapon;
    private CommandMinByWeaponType commandMinByWeaponType;
    private CommandPrintDescending commandPrintDescending;
    private CommandRemoveAtIndex commandRemoveAtIndex;
    private CommandSort commandSort;
    private CommandUpdateID commandUpdateID;
    private EnumMap<CommandType, AbstractCommand> commandFetch;
    private CommandServerExit stop;
    private HashMap<String, AbstractServerCommand> serverCommandFetch;
    private SpaceMarineDBManager spaceMarineDBManager;
    private UserDBManager userDBManager;
    private CommandLogin commandLogin;
    private CommandAuthorization commandAuthorization;
    public ServerApp() throws IOException {
        this.outputUnit = new OutputUnit();
        this.inputUnit = new InputUnit(CONSOLE, new Scanner(System.in));
        try {
            this.spaceMarineDBManager = new SpaceMarineDBManager();
            this.spaceMarineDBManager.init();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
        }
        this.commandFetch = new EnumMap<>(CommandType.class);
        try {
            this.userDBManager = new UserDBManager();
            this.userDBManager.init();
            this.commandLogin = new CommandLogin(this.userDBManager);
            this.commandFetch.put(CommandType.LOGIN, this.commandLogin);
            this.commandAuthorization = new CommandAuthorization(this.userDBManager);
            this.commandFetch.put(CommandType.AUTHORIZATION, this.commandAuthorization);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        this.storage = new CollectionStorage(this.spaceMarineDBManager);
        this.commandAdd = new CommandAdd(storage);
        this.commandFetch.put(CommandType.ADD, this.commandAdd);
        this.commandInfo = new CommandInfo(storage);
        this.commandFetch.put(CommandType.INFO, this.commandInfo);
        this.commandShow = new CommandShow(storage);
        this.commandFetch.put(CommandType.SHOW, this.commandShow);
        this.commandRemoveByID = new CommandRemoveByID(storage);
        this.commandFetch.put(CommandType.REMOVE_BY_ID, this.commandRemoveByID);
        this.commandClear = new CommandClear(storage);
        this.commandFetch.put(CommandType.CLEAR, this.commandClear);
        this.commandFilterGreaterThanMeleeWeapon = new CommandFilterGreaterThanMeleeWeapon(storage);
        this.commandFetch.put(CommandType.FILTER_GREATER_THAN_MELEE_WEAPON, this.commandFilterGreaterThanMeleeWeapon);
        this.commandMinByWeaponType = new CommandMinByWeaponType(storage);
        this.commandFetch.put(CommandType.MIN_BY_WEAPON_TYPE, this.commandMinByWeaponType);
        this.commandPrintDescending = new CommandPrintDescending(storage);
        this.commandFetch.put(CommandType.PRINT_DESCENDING, this.commandPrintDescending);
        this.commandRemoveAtIndex= new CommandRemoveAtIndex(storage);
        this.commandFetch.put(CommandType.REMOVE_AT_INDEX, this.commandRemoveAtIndex);
        this.commandSort= new CommandSort(storage);
        this.commandFetch.put(CommandType.SORT, this.commandSort);
        this.commandUpdateID = new CommandUpdateID(storage);
        this.commandFetch.put(CommandType.UPDATE_ID, this.commandUpdateID);
        this.userCommandInvoker = new UserCommandInvoker(this.commandFetch);
        this.stop = new CommandServerExit(inputUnit, storage);
        this.serverCommandFetch = new HashMap<>();
        this.serverCommandFetch.put("exit", this.stop);
        this.serverCommandInvoker = new ServerCommandInvoker(this.serverCommandFetch);
        this.server = Server.getServer();
    }
    @Override
    public void launch() throws IOException, ClassNotFoundException {
        if (this.spaceMarineDBManager == null | this.userDBManager == null){
            this.stop();
        }
        this.server.start(this.userCommandInvoker, this.serverCommandInvoker, this.inputUnit, this.outputUnit);
    }

    public void stop(){
        this.server.stop();
    }
}
