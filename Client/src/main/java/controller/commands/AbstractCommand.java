package controller.commands;

import commonModule.commands.Command;
import commonModule.commands.CommandType;
import controller.RequestBuilder;
import Network.Connector;
import view.output.OutputUnit;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public abstract class AbstractCommand implements Command<String[], Boolean> {
    protected Connector connector;
    protected RequestBuilder builder;
    protected CommandType commandType;
    protected OutputUnit output;
    public AbstractCommand(CommandType commandType, OutputUnit output) throws SocketException {
        this.connector = Connector.getConnector();
        this.builder = new RequestBuilder();
        this.commandType = commandType;
        this.output = output;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public abstract Boolean execute(String[] command) throws SocketException, IOException;
}
