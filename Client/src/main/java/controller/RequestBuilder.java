package controller;

import commonModule.commands.CommandType;
import commonModule.requests.Request;
import commonModule.requests.NotAuthorizationException;

public class RequestBuilder<T> implements Builder<Request>{
    private CommandType commandType;
    private String userName;
    private T element;
    public void setCommandType(CommandType commandType){
        this.commandType = commandType;
    }

    public void setElement(T element){
        this.element = element;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean isCompleted(){
        return this.commandType!=null & this.userName != null;
    }

    @Override
    public Request build() throws NotAuthorizationException {
        if (this.isCompleted()){
            return new Request(this.commandType, this.userName, this.element);
        }
        return null;
    }
}
