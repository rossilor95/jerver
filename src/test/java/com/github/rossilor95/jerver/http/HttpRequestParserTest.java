package com.github.rossilor95.jerver.http;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestParserTest {
    private final HttpRequestParser httpRequestParser = new HttpRequestParser();

    @Test
    void shouldParseRequest() {
        // Given:
        var testRequest = """
                GET /index.html HTTP/1.1
                Host: example.com
                Accept: text/html
                """;
        var expectedRequestLine = new RequestLine(HttpMethod.GET, URI.create("/index.html"), "HTTP/1.1");
        var expectedHeaders = Map.of("Host", "example.com", "Accept", "text/html");
        var expectedHttpRequest = new HttpRequest(expectedRequestLine, expectedHeaders);
        var inputStream = new ByteArrayInputStream(testRequest.getBytes(StandardCharsets.UTF_8));

        // When:
        var parsedRequest = httpRequestParser.parse(inputStream);

        // Then:
        assertEquals(expectedHttpRequest, parsedRequest);
    }
}
