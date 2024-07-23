package controller.commands;

import commonModule.collectionElements.SpaceMarine;
import commonModule.collectionElements.interfaces.IHaveInputData;
import commonModule.commands.CommandType;
import commonModule.parsers.FormParser;
import commonModule.requests.NotAuthorizationException;
import commonModule.validation.annotations.AnnotationValidator;
import controller.UserContainer;
import view.FillerForm;
import view.input.InputUnit;
import view.output.OutputUnit;

import java.lang.reflect.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.LinkedHashMap;

public class CommandUpdateID extends AbstractCommand{
    private FillerForm fillerForm;
    private AnnotationValidator validator;
    private FormParser formParser;
    public CommandUpdateID(OutputUnit output, InputUnit input) throws SocketException {
        super(CommandType.UPDATE_ID, output);
        this.formParser = new FormParser();
        this.validator = new AnnotationValidator();
        this.fillerForm = new FillerForm(input, output, validator);
    }

    @Override
    public Boolean execute(String[] command) throws SocketException, SocketTimeoutException, IOException {
        if (command.length != 2) {
            this.output.writeln("The wrong number of elements was entered to execute the command" + this.getCommandType().toString() + "(" + (command.length - 1) + " вместо 1)");
            return false;
        }else{
            long id = Long.parseLong(command[1]);
            LinkedHashMap<Field, Object> form = new LinkedHashMap<>();
            try {
                form.put(SpaceMarine.class.getDeclaredField("id"), id);
            } catch (NoSuchFieldException e) {
                return false;
            }
            LinkedHashMap<Field, Object> objectForm = this.fillerForm.fillForm(IHaveInputData.getInputData(SpaceMarine.class), this.formParser);
            for (Field field: objectForm.keySet()){
                form.put(field, objectForm.get(field));
            }
            this.builder.setCommandType(CommandType.UPDATE_ID);
            this.builder.setElement(this.formParser.parse(form, SpaceMarine.class));
            this.builder.setUserName(UserContainer.getUser().getUserName());
            try {
                this.output.writeln(this.connector.getResponceFromServer(this.builder.build()).getElement());
                return true;
            } catch (ClassNotFoundException e) {
                this.output.writeln("The server response could not be recognized.");
                return false;
            } catch (NumberFormatException e){
                this.output.writeln("ID have invalid format. ID must be integer from 1 to " + Long.MAX_VALUE);
                return false;
            } catch (NotAuthorizationException e){
                this.output.writeln(e.getMessage());
                return false;
            }
        }
    }

    @Override
    public String toString(){
        return this.commandType.getName() + " id {element} : update the value of a collection item whose id is equal to the specified one";
    }
}
