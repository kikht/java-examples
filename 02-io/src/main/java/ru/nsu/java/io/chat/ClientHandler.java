package ru.nsu.java.io.chat;

import java.util.*;
import java.io.*;
import java.net.*;
import org.apache.log4j.*;

public class ClientHandler implements Runnable {
    public ClientHandler(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.input = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
        this.output = new BufferedWriter(
                new OutputStreamWriter(
                    socket.getOutputStream()));
    }

    public void run() {
        try {
            while (true) {
                String message = input.readLine();
                server.sendMessage(message);
            }
        } catch (IOException ex) {
            log.info("Client disconnected, while receiving");
            close();
        }
    }

    public void sendMessage(String message) {
        try {
            output.append(message);
            output.newLine();
            output.flush();
        } catch (IOException ex) {
            log.info("Client disconnected, while sending");
            close();
        }
    }

    private void close() {
        server.removeClient(this);
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException ex) {
            log.warn("Exception while closing client socket", ex);
        }
    }

    private final Server server;
    private final Socket socket;
    private final BufferedReader input;
    private final BufferedWriter output;

    private static Logger log = Logger.getLogger(ClientHandler.class);
}
