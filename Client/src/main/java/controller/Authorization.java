package controller;

import commonModule.utils.App;
import controller.commands.CommandAuthorization;
import controller.commands.CommandLogin;
import view.input.InputUnit;
import view.output.OutputUnit;

import java.io.IOException;
import java.net.SocketException;

public class Authorization implements App {
    private InputUnit inputUnit;
    private OutputUnit outputUnit;
    private CommandLogin commandLogin;
    private CommandAuthorization commandAuthorization;
    public Authorization(InputUnit inputUnit, OutputUnit outputUnit) throws SocketException {
        this.inputUnit = inputUnit;
        this.outputUnit = outputUnit;
        this.commandLogin = new CommandLogin(this.outputUnit, this.inputUnit);
        this.commandAuthorization = new CommandAuthorization(this.outputUnit, this.inputUnit);
    }
    @Override
    public void launch(){
        while (UserContainer.isEmpty()){
            this.outputUnit.write("If you want to register, enter any character (If you want to log in, do not enter anything): ");
            String inputData = this.inputUnit.readln();
            if (inputData != ""){
                try {
                    this.commandAuthorization.execute(new String[1]);
                } catch (IOException e) {
                }
            }else{
                this.commandLogin.execute(new String[1]);
            }
        }
    }
}
