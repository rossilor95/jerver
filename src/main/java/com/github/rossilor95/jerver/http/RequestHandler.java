package com.github.rossilor95.jerver.http;

import com.github.rossilor95.jerver.Jerver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(Jerver.class);

    private final Socket clientSocket;
    private final HttpRequestParser httpRequestParser;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.httpRequestParser = new HttpRequestParser();
    }

    public void run() throws IOException {
        var writer = new PrintWriter(clientSocket.getOutputStream(), true);

        // Read the request from the client and parse it into an HttpRequest object
        HttpRequest httpRequest = httpRequestParser.parse(clientSocket.getInputStream());
        logger.info("Received request: " + httpRequest);

        // Write the response back to the client
        writer.println(generateResponse());

        // Close the client socket
        clientSocket.close();
    }

    private String generateResponse() {
        return """
                HTTP/1.1 200 OK
                Content-Type: text/plain
                                
                Hello, client!""";
    }
}
