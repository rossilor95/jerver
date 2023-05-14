package com.github.rossilor95.jerver.http;

import java.util.List;

/**
 * Represents an HTTP request, consisting of a {@link RequestLine} and a list of headers.
 */
record HttpRequest(RequestLine requestLine, List<String> headers) {
}
