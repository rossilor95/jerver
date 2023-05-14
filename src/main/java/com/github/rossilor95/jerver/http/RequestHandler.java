package com.github.rossilor95.jerver.http;

import com.github.rossilor95.jerver.Jerver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.util.Collections;

public class RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(Jerver.class);

    private final Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() throws IOException {
        var writer = new PrintWriter(clientSocket.getOutputStream(), true);

        // Read the request from the client and parse it into an HttpRequest object
        HttpRequest httpRequest = parseRequest(clientSocket.getInputStream());
        logger.info("Received request: " + httpRequest);

        // Write the response back to the client
        writer.println(generateResponse());

        // Close the client socket
        clientSocket.close();
    }

    private HttpRequest parseRequest(InputStream inputStream) throws IOException {
        var bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        var requestLine = parseRequestLine(bufferedReader.readLine());
        return new HttpRequest(requestLine, Collections.emptyList());
    }

    private RequestLine parseRequestLine(String str) {
        String[] elements = str.split("\\s+");
        var method = HttpMethod.valueOf(elements[0]);
        var uri = URI.create(elements[1]);
        var version = elements[2];
        return new RequestLine(method, uri, version);
    }

    private String generateResponse() {
        return """
                HTTP/1.1 200 OK
                Content-Type: text/plain
                                
                Hello, client!""";
    }
}
