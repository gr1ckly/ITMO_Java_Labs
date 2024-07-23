package Network;

import commonModule.requests.Request;
import commonModule.utils.Response;
import commonModule.utils.SerializeManager;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Connector {
    private final int SERVER_PORT;
    private static final int PACKET_SIZE = 16384;
    private static Connector instance;
    private DatagramSocket socket;
    private SocketAddress address;
    private UDPSender sender;
    private UDPListener listener;
    private Connector(int serverPort) throws SocketException {
        SERVER_PORT = serverPort;
        this.address = new InetSocketAddress("localhost", this.SERVER_PORT);
        this.socket = new DatagramSocket();
        this.socket.connect(address);
        this.sender = new UDPSender(this.socket);
        this.listener = new UDPListener(this.socket, Connector.PACKET_SIZE);
    }

    public static Connector getConnector() throws SocketException {
        if (Connector.instance == null){
            boolean repeatable = true;
            int port = 19858;
            while (repeatable) {
                System.out.print("Write port to connect: ");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.nextLine();
                try {
                    port = Integer.parseInt(answer);
                    repeatable = false;
                }catch (NumberFormatException e){
                }
            }
            Connector.instance = new Connector(port);
        }
        return Connector.instance;
    }

    public Response<String> getResponceFromServer(Request request) throws IOException, ClassNotFoundException, SocketException {
        this.sender.send(SerializeManager.serialize(request));
        return (Response<String>) SerializeManager.deserialize(this.listener.listen());
    }

    public void stop(){
        this.socket.close();
    }
}
