package view;

import commonModule.utils.App;
import controller.Authorization;
import controller.Invoker;
import controller.commands.CommandAuthorization;
import controller.commands.CommandLogin;
import view.input.InputMode;
import view.input.InputUnit;
import view.output.OutputUnit;
import commonModule.requests.Request;
import commonModule.commands.CommandType;

import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;

/**
 * Класс-наследник {@link App}, используемый для запуска работы программы.
 */
public class ConsoleApp implements App {
    private InputUnit input;
    private Invoker invoker;
    private OutputUnit output;
    private Request request;
    private Authorization authorization;

    public ConsoleApp() throws SocketException {
        this.input = new InputUnit(InputMode.CONSOLE, new Scanner(System.in));
        this.output = new OutputUnit();
        this.invoker = new Invoker(this.output, this.input);
        this.authorization = new Authorization(this.input, this.output);
    }

    /**
     * Метод, запускающий работу программы и определяющий первоначальный алгоритм действий при работе с разными типами команд.
     */
    @Override
    public void launch(){
        this.authorization.launch();
        while(this.input.getMode() != InputMode.STOP) {
            if (this.input.canRead()) {
                if (this.input.getMode() == InputMode.CONSOLE) {
                    this.output.write("Write command: ");
                }
                String inputString = this.input.readln();
                if (inputString == null){
                    break;
                }
                inputString = inputString.trim();
                String[] data = inputString.split("\\s+");

                CommandType command = CommandType.getCommand(data[0]);
                if (command != null) {
                    try {
                        invoker.execute(command, data);
                    }catch (IOException e){
                        this.output.writeln("An error occurred when connecting to the server.");
                    }
                } else {
                    this.output.writeln("Command \"" + inputString + "\" does not exist. Write help to see the list of commands");
                }
            }else{
                this.input.removeCurrentInputDevice();
            }
        }
        this.output.writeln("Shutdown of the program.");
    }
}
