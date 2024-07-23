package controller.commands;

import commonModule.collectionElements.SpaceMarine;
import commonModule.collectionElements.interfaces.IHaveInputData;
import commonModule.commands.CommandType;
import commonModule.parsers.FormParser;
import commonModule.requests.NotAuthorizationException;
import commonModule.validation.annotations.AnnotationValidator;
import controller.UserContainer;
import validation.AnnotationDescriptor;
import view.FillerForm;
import view.input.InputUnit;
import view.output.OutputUnit;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.LinkedHashMap;

public class CommandAdd extends AbstractCommand{
    protected InputUnit input;
    protected FillerForm fillerForm;
    protected AnnotationValidator validator;
    protected FormParser formParser;
    public CommandAdd(OutputUnit output, InputUnit input) throws SocketException {
        super(CommandType.ADD, output);
        this.validator = new AnnotationValidator();
        this.input = input;
        this.formParser = new FormParser<>();
        this.fillerForm = new FillerForm(this.input, this.output, this.validator);
    }

    @Override
    public Boolean execute(String[] command) throws SocketException, IOException, SocketTimeoutException {
        if (command.length > 1) {
            this.output.writeln("The wrong number of elements was entered to execute the command" + this.getCommandType().toString() + "(" + (command.length - 1) + " вместо 0)");
            return false;
        }else{
            LinkedHashMap form = this.fillerForm.fillForm(IHaveInputData.getInputData(SpaceMarine.class), this.formParser);
            this.builder.setCommandType(CommandType.ADD);
            this.builder.setUserName(UserContainer.getUser().getUserName());
            SpaceMarine spaceMarine = (SpaceMarine) this.formParser.parse(form, SpaceMarine.class);
            spaceMarine.setOwnerName(UserContainer.getUser().getUserName());
            this.builder.setElement(spaceMarine);
            try {
                this.output.writeln(this.connector.getResponceFromServer(this.builder.build()).getElement());
                return true;
            } catch (ClassNotFoundException e) {
                this.output.writeln("The server response could not be recognized.");
                return false;
            } catch (NotAuthorizationException e){
                this.output.writeln(e.getMessage());
                return false;
            }
        }
    }
    @Override
    public String toString(){
        return this.commandType.getName() + " {element} : add a new item to the collection";
    }
}
