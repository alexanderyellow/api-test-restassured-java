package org.example.actions;

/// Describes a single API endpoint: HTTP method, path template, and expected success status code.
/// Path parameters use RestAssured's {name} syntax, e.g. "/resource/{id}".
public record Endpoint(HttpMethod method, String path, int defaultStatusCode) {}
