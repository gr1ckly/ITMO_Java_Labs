package Network;

import commonModule.network.Listener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

public class UDPListener implements Listener<byte[]> {
    private int packetSize;
    private DatagramSocket socket;
    private DatagramPacket packet;
    private boolean repeatable;
    public UDPListener(DatagramSocket socket, int packetSize){
        this.socket = socket;
        this.packetSize = packetSize;
        this.packet = new DatagramPacket(new byte[this.packetSize], this.packetSize);
        this.repeatable = true;
    }
    @Override
    public byte[] listen() throws SocketException, SocketTimeoutException, IOException {
        byte[] fullData;
        int length;
        int index = 0;
        this.socket.setSoTimeout(50000);
        this.socket.receive(this.packet);
        byte[] bytePacket = this.packet.getData();
        length = ByteBuffer.wrap(bytePacket).getInt();
        fullData = new byte[length];
        while (index < Math.min(bytePacket.length - 4, length)){
            fullData[index] = bytePacket[index + 4];
            index += 1;
        }
        while (length > index){
            this.packet = new DatagramPacket(new byte[Math.min(this.packetSize, length - index)], Math.min(this.packetSize, length - index));
            this.socket.receive(this.packet);
            bytePacket = this.packet.getData();
            for (int i =0; i<Math.min(bytePacket.length, length - index); i++){
                fullData[index + i] = bytePacket[i];
            }
            index += bytePacket.length;
        }
        return fullData;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setPacketSize(int packetSize) {
        this.packetSize = packetSize;
    }

    public int getPacketSize() {
        return packetSize;
    }
}
