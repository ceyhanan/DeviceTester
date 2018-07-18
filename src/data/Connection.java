package data;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * This represents a connection via a socket, either server or client
 *
 * @author Craig
 *
 */
public class Connection {

    private InetAddress addr;
    private int port, id;
    private DatagramSocket clientSocket;

    public Connection(DatagramSocket socket, InetAddress addr, int port, int id) {
        this.addr = addr;
        this.port = port;
        this.clientSocket = socket;
        this.id = id;
    }

    public void send(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);

        try {
            clientSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] receive() {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        try {
            clientSocket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] data = packet.getData();
        return data;
    }

    public int getPort() {
        return this.port;
    }

    public InetAddress getAddress() {
        return this.addr;
    }

    public void close() {
        new Thread() {
            @Override
            public void run() {
                synchronized (clientSocket) {
                    clientSocket.close();
                }
            }
        }.start();
    }
}
