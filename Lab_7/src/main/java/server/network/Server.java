package server.network;

import controller.ServerCommandInvoker;
import controller.UserCommandInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.InputUnit;
import server.OutputUnit;
import server.ServerMode;
import server.multiThreading.MainTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.*;

import static server.ServerMode.STOP;

public class Server {
    private static Server instance;
    private static int LOCAL_PORT;
    private UserCommandInvoker currentUserInvoker;
    private DatagramChannel channel;
    private UDPSender sender;
    private SocketAddress address;
    private UDPListener listener;
    private ServerMode serverMode;
    private ServerCommandInvoker serverCommandInvoker;
    private InputUnit inputUnit;
    private OutputUnit outputUnit;
    private ForkJoinPool forkJoinPool;
    private ExecutorService handler;
    private ExecutorService sendExecutor;
    private Server() throws IOException {
        this.address = new InetSocketAddress("localhost", this.LOCAL_PORT);
        this.channel = DatagramChannel.open();
        this.channel.configureBlocking(false);
        this.channel.bind(this.address);
        this.serverMode = ServerMode.START;
        this.listener = new UDPListener(this.channel);
        this.sender = new UDPSender(this.channel);
        this.forkJoinPool = new ForkJoinPool();
        this.handler = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.sendExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }
    public static Server getServer() throws IOException {
        if (Server.instance == null){
            Server.instance = new Server();
        }
        return Server.instance;
    }

    public static void setLocalPort(int port){
        Server.LOCAL_PORT = port;
    }

    public void start(UserCommandInvoker invoker, ServerCommandInvoker serverCommandInvoker, InputUnit inputUnit, OutputUnit outputUnit) throws IOException, ClassNotFoundException {
        this.currentUserInvoker = invoker;
        this.serverCommandInvoker = serverCommandInvoker;
        this.inputUnit = inputUnit;
        this.outputUnit = outputUnit;
        while (this.serverMode != STOP){
            if (System.in.available() > 0){
                String data = this.inputUnit.readln();
                this.outputUnit.writeln(this.serverCommandInvoker.execute(data.split(" ")));
            }
            byte[] buffer = this.listener.listen();
            SocketAddress clientAddress = this.listener.getAddress();
            if (buffer != null) {

                RecursiveAction action = new MainTask(this.sendExecutor, this.handler, this.sender, this.currentUserInvoker, clientAddress, buffer);
                this.forkJoinPool.invoke(action);
            }
        }
        this.handler.shutdown();
        this.sendExecutor.shutdown();
        this.forkJoinPool.shutdown();
        this.channel.close();
    }

    public ServerMode getServerMode() {
        return serverMode;
    }

    public void setServerMode(ServerMode serverMode) {
        this.serverMode = serverMode;
    }

    public void stop(){
        this.serverMode = STOP;
    }
}
