package com.projetenchere.common.network;

import com.projetenchere.common.network.socket.SSLSocketFactory;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.*;

public class Server extends Thread{
    private final int port;
    Map<Headers, IDataHandler> handlers = new HashMap<>();
    private final Map<ClientAcceptor<?>, Socket> connectedClients = new HashMap<>();

    public Server(int port) {
        this.port = port;
    }

    public synchronized void addHandler(Headers header, IDataHandler replyer) {
        for (ClientAcceptor<?> client : connectedClients.keySet()) {
            client.addHandler(header, replyer);
        }
        this.handlers.put(header, replyer);
    }

    public synchronized void removeHandler(Headers header) {
        for (ClientAcceptor<?> client : connectedClients.keySet()) {
            client.removeHandler(header);
        }
        this.handlers.remove(header);
    }

    public synchronized void stopConnection(ClientAcceptor<?> clientAcceptor) {
        if (this.connectedClients.containsKey(clientAcceptor)) {
            Socket s = this.connectedClients.get(clientAcceptor);
            try {
                s.close();
                clientAcceptor.interrupt();
                this.connectedClients.remove(clientAcceptor, s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void run() {
        while (true) {
            SSLSocketFactory context = new SSLSocketFactory();
            try (SSLServerSocket ss = context.createServerSocket(port)) {
                SSLSocket s = (SSLSocket) ss.accept();
                ClientAcceptor<? extends Serializable> t = new ClientAcceptor<>(
                        handlers,
                        new ObjectInputStream(s.getInputStream()),
                        new ObjectOutputStream(s.getOutputStream()),
                        this
                );
                this.connectedClients.put(t, s);
                t.start();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }
}
