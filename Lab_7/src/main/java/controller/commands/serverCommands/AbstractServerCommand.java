package controller.commands.serverCommands;

import commonModule.commands.Command;
import server.OutputUnit;

public abstract class AbstractServerCommand implements Command<String[], String> {
    public AbstractServerCommand(){
    }
    public abstract String execute(String[] data);
}
