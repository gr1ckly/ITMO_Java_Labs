package server.multiThreading;

import commonModule.requests.Request;
import commonModule.utils.Response;
import controller.UserCommandInvoker;

import java.util.concurrent.Callable;

public class HandleTask implements Callable<Response> {
    private UserCommandInvoker userInvoker;
    private Request request;
    public HandleTask(Request request, UserCommandInvoker userInvoker){
        this.request = request;
        this.userInvoker = userInvoker;
    }
    @Override
    public Response call(){
        return this.userInvoker.execute(request);
    }
}
