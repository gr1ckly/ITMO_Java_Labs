package server.network;

import commonModule.network.ServerSender;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class UDPSender implements ServerSender<byte[], SocketAddress> {
    private DatagramChannel channel;

    private static final int BUFFER_SIZE = 1024;
    public UDPSender(DatagramChannel channel){
        this.channel = channel;
    }
    @Override
    public synchronized void send(byte[] bytes, SocketAddress socketAddress) throws IOException{
        ByteBuffer buffer = ByteBuffer.allocate(Math.min(UDPSender.BUFFER_SIZE, bytes.length + 4));
        buffer.putInt(bytes.length);
        int start = 0;
        int end = Math.min(UDPSender.BUFFER_SIZE - 4, bytes.length);
        buffer.put(Arrays.copyOfRange(bytes, start, end));
        buffer = buffer.flip();
        this.channel.send(buffer, socketAddress);
        while (end != bytes.length){
            start = end;
            end = Math.min(bytes.length, start + UDPSender.BUFFER_SIZE);
            buffer = buffer.clear();
            buffer = ByteBuffer.wrap(Arrays.copyOfRange(bytes, start, end));
            buffer = buffer.flip();
            this.channel.send(buffer, socketAddress);
        }
    }
}
