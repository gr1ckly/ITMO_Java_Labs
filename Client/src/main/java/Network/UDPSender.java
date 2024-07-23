package Network;

import commonModule.network.ClientSender;
import commonModule.network.ServerSender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPSender implements ClientSender<byte[]> {
    private DatagramSocket socket;
    public UDPSender(DatagramSocket socket){
        this.socket = socket;
    }
    @Override
    public void send(byte[] byteArray) throws IOException{
        DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length);
        this.socket.send(packet);
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    public DatagramSocket getSocket() {
        return socket;
    }
}
