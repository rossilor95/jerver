package com.github.rossilor95.jerver;

import com.github.rossilor95.jerver.handler.RequestHandler;
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
            logger.info("Jerver listening on port {}", port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Client connected: {}", clientSocket.getInetAddress());
                var requestHandler = new RequestHandler(clientSocket);
                requestHandler.run();
            }
        } catch (IOException e) {
            logger.error("Error accepting client connection: {}", e.getMessage());
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}