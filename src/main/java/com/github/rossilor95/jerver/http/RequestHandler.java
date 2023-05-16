package com.github.rossilor95.jerver.http;

import com.github.rossilor95.jerver.Jerver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private HttpRequest parseRequest(InputStream inputStream) {
        List<String> lines = parseLines(inputStream);
        RequestLine requestLine = parseRequestLine(lines);
        Map<String, String> headers = parseHeaders(lines);
        return new HttpRequest(requestLine, headers);
    }

    private List<String> parseLines(InputStream inputStream) {
        var bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader.lines().toList();
    }

    private RequestLine parseRequestLine(List<String> lines) {
        // Gets the first line and splits it around spaces
        String[] elements = lines.get(0).split("\\s+");
        var method = HttpMethod.valueOf(elements[0]);
        var uri = URI.create(elements[1]);
        var version = elements[2];
        return new RequestLine(method, uri, version);
    }

    private Map<String, String> parseHeaders(List<String> lines) {
        if (lines.size() == 1) {
            return Collections.emptyMap();
        }
        return lines.stream()
                .skip(1) // Skip the request line
                .map(headerLine -> headerLine.split(":", 2))
                .filter(headerParts -> headerParts.length == 2)
                .collect(Collectors.toMap(
                        headerParts -> headerParts[0].trim(),
                        headerParts -> headerParts[1].trim(),
                        (headerValue1, headerValue2) -> headerValue1,
                        HashMap::new
                ));
    }

    private String generateResponse() {
        return """
                HTTP/1.1 200 OK
                Content-Type: text/plain
                                
                Hello, client!""";
    }
}
