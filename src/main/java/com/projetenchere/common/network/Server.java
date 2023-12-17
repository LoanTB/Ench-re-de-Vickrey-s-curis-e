package com.projetenchere.common.network;

import com.projetenchere.common.network.socket.SSLSocketFactory;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Server extends Thread{
    private int port;
    Map<Headers, IDataHandler> handlers = new HashMap<>();
    private Set<ClientAcceptor> connectedClients = new HashSet<>();

    public Server(int port) {
        this.port = port;
    }

    public synchronized void addHandler(Headers header, IDataHandler replyer) {
        for (ClientAcceptor client : connectedClients) {
            client.addHandler(header, replyer);
        }
        this.handlers.put(header, replyer);
    }

    public synchronized void removeHandler(Headers header) {
        for (ClientAcceptor client : connectedClients) {
            client.removeHandler(header);
        }
        this.handlers.remove(header);
    }

    public void run() {
        while (true) {
            SSLSocketFactory context = new SSLSocketFactory();
            try (SSLServerSocket ss = context.createServerSocket(port)) {
                SSLSocket s = (SSLSocket) ss.accept();
                ClientAcceptor<? extends Serializable> t = new ClientAcceptor<>(
                        handlers,
                        new ObjectInputStream(s.getInputStream()),
                        new ObjectOutputStream(s.getOutputStream())
                );
                this.connectedClients.add(t);
                t.start();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }
}
