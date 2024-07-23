package controller.commands;

import commonModule.collectionElements.interfaces.IHaveInputData;
import commonModule.commands.CommandType;
import commonModule.parsers.FormParser;
import commonModule.requests.NotAuthorizationException;
import commonModule.requests.Request;
import commonModule.utils.Response;
import commonModule.utils.User;
import commonModule.validation.annotations.AnnotationValidator;
import controller.UserContainer;
import view.FillerForm;
import view.input.InputUnit;
import view.output.OutputUnit;

import javax.swing.*;
import java.io.IOException;
import java.net.SocketException;

public class CommandAuthorization extends AbstractCommand{
    private InputUnit inputUnit;
    private FillerForm fillerForm;
    private FormParser formParser;
    public CommandAuthorization(OutputUnit outputUnit, InputUnit inputUnit) throws SocketException {
        super(CommandType.AUTHORIZATION, outputUnit);
        this.inputUnit = inputUnit;
        this.fillerForm = new FillerForm(this.inputUnit, this.output, new AnnotationValidator());
        this.formParser = new FormParser();
    }
    @Override
    public Boolean execute(String[] command) throws SocketException, IOException {
        User user = (User) this.formParser.parse(this.fillerForm.fillForm(IHaveInputData.getInputData(User.class), this.formParser), User.class);
        user.setAutomatically();
        this.builder.setCommandType(CommandType.AUTHORIZATION);
        this.builder.setElement(user);
        this.builder.setUserName(user.getUserName());
        Request request = null;
        try {
            request = this.builder.build();
            try {
                Response response = this.connector.getResponceFromServer(request);
                if ((Boolean) response.getElement()){
                    UserContainer.putUser(user);
                    return true;
                }else{
                    this.output.writeln("Such a user already exists.");
                }
            } catch (ClassNotFoundException | IOException e) {
                this.output.writeln("The server response could not be recognized.");
            }
        } catch (NotAuthorizationException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
