package ru.nsu.java.io.chat;

import java.util.*;
import java.io.*;
import java.net.*;
import org.apache.log4j.*;

public class Server {
    public static void main(String[] args) {
        Server server = new Server();
        server.acceptClients();
    }

    public void acceptClients() {
        try {
            log.info("Binding to port");
            ServerSocket socket = new ServerSocket(5555);
            log.info("Server started, waiting for client...");
            while (true) {
                Socket client = socket.accept();
                log.info("Client connected from " + 
                        client.getRemoteSocketAddress());
                ClientHandler ch = new ClientHandler(this, client);
                synchronized (lock) {
                    clients.add(ch);
                }
                new Thread(ch).start();
            }
        } catch (IOException ex) {
            log.fatal("Exception in server thread", ex);
        }
    }

    public void sendMessage(String message) {
        synchronized(lock) {
            ArrayList<ClientHandler> temp = new ArrayList<>(clients);
            for (ClientHandler ch : temp) {
                ch.sendMessage(message);
            }
        }
    }

    public void removeClient(ClientHandler ch) {
        synchronized(lock) {
            clients.remove(ch);
        }
    }

    private Collection<ClientHandler> clients = new ArrayList<>();
    private Object lock = new Object();

    private static Logger log = Logger.getLogger(Server.class);
}
