package controller;

import commonModule.commands.Invoker;
import controller.commands.serverCommands.AbstractServerCommand;

import java.util.HashMap;

public class ServerCommandInvoker implements Invoker<String[], String> {
    private HashMap<String, AbstractServerCommand> serverCommandFetch;
    public ServerCommandInvoker(HashMap<String, AbstractServerCommand> serverCommandFetch){
        this.serverCommandFetch = serverCommandFetch;
    }
    @Override
    public String execute(String[] s) {
        AbstractServerCommand command = this.serverCommandFetch.get(s[0]);
        if (command != null){
            return command.execute(s);
        }
        return "Такой команды для сервера не существует.";
    }
}
