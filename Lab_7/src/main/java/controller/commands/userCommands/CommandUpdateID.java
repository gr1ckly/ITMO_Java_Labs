package controller.commands.userCommands;

import commonModule.collectionElements.SpaceMarine;
import commonModule.collectionElements.interfaces.IHaveID;
import commonModule.collectionElements.interfaces.IHaveInputData;
import commonModule.requests.Request;
import commonModule.utils.Response;
import model.CollectionStorage;

public class CommandUpdateID extends AbstractCommandWithDataBase<Request, CollectionStorage> {
    public CommandUpdateID(CollectionStorage storage){
        super(storage);
    }

    @Override
    public synchronized Response<String> execute(Request request){
        IHaveID object = (IHaveID) request.getElement();
        return this.storage.updateByID((SpaceMarine) request.getElement(), object.getId(), request.getUserName());
    }
}
