package controller.commands.userCommands;
import commonModule.utils.Response;
import model.Storage;
import commonModule.requests.AbstractRequest;

/**
 * Наследуется от {@link AbstractCommand}
 * @param <T> T extends {@link AbstractRequest}
 */
public abstract class AbstractCommandWithDataBase<T extends AbstractRequest, S extends Storage> extends AbstractCommand<T> {
    protected S storage;

    /**
     *
     * @param storage CollectionStorage - класс, управляющий хранилищем элементов коллекции
     */
    public AbstractCommandWithDataBase(S storage){
        super();
        this.storage = storage;
    }

    public abstract Response<String> execute(T request);
}
