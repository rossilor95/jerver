package com.github.rossilor95.jerver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Jerver {
    private static final Logger logger = LoggerFactory.getLogger(Jerver.class);
    private static final int DEFAULT_PORT = 8080;

    private int port;

    public Jerver(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        var jerver = new Jerver(DEFAULT_PORT);
        jerver.start();
    }

    private void start() {
        try (var serverSocket = new ServerSocket(port)) {
            logger.info("Jerver started on port {}", port);
            while(true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Client connected: {}", clientSocket.getInetAddress());
            }
        } catch (IOException e) {
            logger.error("Error starting server: {}", e.getMessage());
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}