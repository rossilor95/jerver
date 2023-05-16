package com.github.rossilor95.jerver.http;

import java.util.Map;

/**
 * Represents an HTTP request, consisting of a {@link RequestLine} and a list of headers.
 */
record HttpRequest(RequestLine requestLine, Map<String, String> headers) {
}
