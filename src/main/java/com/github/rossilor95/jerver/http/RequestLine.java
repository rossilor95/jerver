package com.github.rossilor95.jerver.http;

import java.net.URI;

/**
 * Represents the request line of an HTTP request, consisting of the HTTP method, the URI of the requested resource, and
 * the HTTP version.
 */
record RequestLine(HttpMethod httpMethod, URI uri, String httpVersion) {
}
