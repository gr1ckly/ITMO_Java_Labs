package controller.commands.serverCommands;

import model.CollectionStorage;
import server.InputUnit;
import server.network.Server;

import java.io.IOException;

public class CommandServerExit extends AbstractServerCommand{
    private InputUnit inputUnit;
    private CollectionStorage storage;
    private Server server;
    public CommandServerExit(InputUnit inputUnit, CollectionStorage storage){
        this.inputUnit = inputUnit;
        this.storage = storage;
        try {
            this.server = Server.getServer();
        } catch (IOException e) {}
    }
    @Override
    public String execute(String[] data){
        if (data.length>1){
            return "Для выполнения команды " + data[0] + " было введено неверное количество аргументов (" + (data.length-1) + " вместо 0)";
        }else{
            this.inputUnit.stopInput();
            this.server.stop();
            return "Завершение работы сервера.";
        }
    }
}
