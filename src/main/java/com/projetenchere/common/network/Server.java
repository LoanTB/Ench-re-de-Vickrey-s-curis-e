package com.projetenchere.common.network;

import com.projetenchere.common.network.Handlers.client.ClientHandler;
import com.projetenchere.common.network.Handlers.data.IDataHandler;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Server extends Thread{
    Map<Headers, IDataHandler> handlers = new HashMap<>();
    Set<ClientHandler> connectedClients = new HashSet<>();

    public synchronized void addHandler(Headers header, IDataHandler replyer) {
        for (ClientHandler client : connectedClients) {
            client.addHandler(header, replyer);
        }
        this.handlers.put(header, replyer);
    }

    public synchronized void removeHandler(Headers header) {
        for (ClientHandler client : connectedClients) {
            client.removeHandler(header);
        }
        this.handlers.remove(header);
    }

    public void run() {
        while (true) {
            SocketFactory context = new SocketFactory();
            try (SSLServerSocket ss = context.createServerSocket(24683)) {
                SSLSocket s = (SSLSocket) ss.accept();
                Thread t = new ClientHandler(
                        handlers,
                        new ObjectInputStream(s.getInputStream()),
                        new ObjectOutputStream(s.getOutputStream())
                );
                t.start();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }
}
