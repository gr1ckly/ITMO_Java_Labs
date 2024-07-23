package server.network;

import commonModule.network.Listener;
import model.CollectionStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ServerMode;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPListener implements Listener<byte[]> {
    private DatagramChannel channel;
    private static final int BUFFER_SIZE = 16384;
    private ByteBuffer buffer;
    private SocketAddress address;
    public UDPListener(DatagramChannel channel) {
        this.channel = channel;
        this.buffer = ByteBuffer.allocate(UDPListener.BUFFER_SIZE);
    }
    @Override
    public byte[] listen() throws IOException{
        buffer.clear();
        SocketAddress currentAddress = null;
        currentAddress = this.channel.receive(buffer);
        if (currentAddress != null){
            this.address = currentAddress;
            return buffer.array();
        }
        return null;
    }

    public SocketAddress getAddress(){
        return this.address;
    }
}
