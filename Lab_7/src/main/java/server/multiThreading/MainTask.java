package server.multiThreading;

import commonModule.requests.Request;
import commonModule.utils.Response;
import commonModule.utils.SerializeManager;
import controller.UserCommandInvoker;
import server.network.UDPSender;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.*;

public class MainTask extends RecursiveAction {
    private ExecutorService sendExecutor;
    private ExecutorService handler;
    private byte[] bytesRequest;
    private UDPSender udpSender;
    private SocketAddress clientAddress;
    private UserCommandInvoker userInvoker;
    public MainTask(ExecutorService sendExecutor, ExecutorService handler, UDPSender udpSender, UserCommandInvoker userInvoker, SocketAddress clientAddress, byte[] bytes){
        this.sendExecutor = sendExecutor;
        this.handler = handler;
        this.udpSender = udpSender;
        this.userInvoker = userInvoker;
        this.clientAddress = clientAddress;
        this.bytesRequest = bytes;
    }
    @Override
    public void compute(){
        Request request = null;
        try {
            request = (Request) SerializeManager.deserialize(this.bytesRequest);
            HandleTask handle = new HandleTask(request, this.userInvoker);
            Future<Response> result = this.handler.submit(handle);
            try {
                Response response = result.get();
                byte[] bytesResponse = SerializeManager.serialize(response);
                SendTask send = new SendTask(this.clientAddress, this.udpSender, bytesResponse);
                this.sendExecutor.submit(send);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
