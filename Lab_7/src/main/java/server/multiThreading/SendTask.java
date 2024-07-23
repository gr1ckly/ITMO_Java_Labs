package server.multiThreading;

import commonModule.network.ServerSender;
import server.network.UDPSender;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.concurrent.Callable;

public class SendTask implements Runnable {
    private SocketAddress socketAddress;
    private UDPSender sender;
    private byte[] bytesResponse;
    public SendTask(SocketAddress address, UDPSender sender, byte[] bytesResponse){
        this.socketAddress = address;
        this.sender = sender;
        this.bytesResponse = bytesResponse;
    }
    @Override
    public void run() {
        try {
            this.sender.send(bytesResponse, socketAddress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
