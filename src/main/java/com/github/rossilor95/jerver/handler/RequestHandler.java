package com.github.rossilor95.jerver.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler {
    private Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             var writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Read the request from the client
            String request = reader.readLine();
            System.out.println("Received request: " + request);

            // Write the response back to the client
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: text/plain");
            writer.println();
            writer.println("Hello, client!");

            // Close the client socket
            clientSocket.close();
        }

    }
}
