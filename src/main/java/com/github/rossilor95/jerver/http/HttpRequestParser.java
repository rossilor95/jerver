package com.github.rossilor95.jerver.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This utility class encapsulates helper methods to parse incoming HTTP requests.
 */
class HttpRequestParser {
    /**
     * Parses an HTTP request from an input stream.
     *
     * @param inputStream the input stream containing the HTTP request
     * @return the parsed HttpRequest object
     */
    HttpRequest parse(InputStream inputStream) {
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
                        (headerValue1, headerValue2) -> headerValue1
                ));
    }
}
