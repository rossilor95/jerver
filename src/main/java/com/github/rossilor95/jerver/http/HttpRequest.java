package com.github.rossilor95.jerver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * Represents an HTTP request, consisting of a {@link RequestLine} and a list of headers.
 */
public class HttpRequest {
    private final RequestLine requestLine;
    private final List<String> headers;

    public HttpRequest(RequestLine requestLine, List<String> headers) {
        this.requestLine = requestLine;
        this.headers = headers;
    }

    public HttpRequest parse(InputStream inputStream) throws IOException {
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

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public List<String> getHeaders() {
        return headers;
    }
}
