package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import data.Connection;
import data.Packet;
import data.PacketHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ceyha
 */
public class UDPServer implements Runnable {

    private int port;
    private DatagramSocket socket;
    private boolean running;

    /* Threads */
    private Thread send, receive, process;

    public static ArrayList<Connection> CLIENTS = new ArrayList<Connection>();

    public UDPServer(int port) {
        this.port = port;

        try {
            this.init();
        } catch (SocketException e) {
            System.err.println("UDP server init error! " + e.getMessage());
        }
    }

    public void init() throws SocketException {
        this.socket = new DatagramSocket(this.port);
        process = new Thread(this, "server_process");
        process.start();
    }

    public int getPort() {
        return port;
    }

    public void send(final Packet packet) {
        send = new Thread("send_thread") {
            @Override
            public void run() {
                DatagramPacket dgpack = new DatagramPacket(
                        packet.getData(),
                        packet.getData().length,
                        packet.getAddr(),
                        packet.getPort()
                );

                try {
                    socket.send(dgpack);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        send.start();
    }

    public void broadcast(byte[] data) {
        for (Connection c : CLIENTS) {
            send(new Packet(data, c.getAddress(), c.getPort()));
        }
    }

    /**
     * Wait for input... and use a PacketHandler to process the packet
     *
     * @param handler The packet handler
     */
    public void receive(final PacketHandler handler) {
        receive = new Thread("receive_thread") {
            @Override
            public void run() {
                while (running) {
                    byte[] buffer = new byte[1024];
                    DatagramPacket dgpacket = new DatagramPacket(buffer, buffer.length);

                    try {
                        socket.receive(dgpacket);
                    } catch (IOException e) {
                        System.err.println("UDPServer receive error! " + e.getMessage());
                    }

                    //handler.process(new Packet(dgpacket.getData(), new Connection(socket, dgpacket.getAddress(), dgpacket.getPort(), UID.getIdentifier())));
                    handler.process(new Packet(dgpacket.getData(), dgpacket.getAddress(), dgpacket.getPort()));
                }
            }
        };

        receive.start();
    }

    public void stop() {
        this.socket.close();
        running = false;
    }

    /**
     * The run method of this runnable thread object.
     */
    @Override
    public void run() {
        running = true;
        System.out.println("Server started on port " + port);
    }
}
