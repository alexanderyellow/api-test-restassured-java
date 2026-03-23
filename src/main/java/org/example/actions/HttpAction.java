package org.example.actions;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.clients.ApiClient;

import java.util.LinkedHashMap;
import java.util.Map;

/// Generic HTTP action that works for any endpoint, HTTP method, request body, and path parameters.
/// Replaces the need for per-entity action subclasses — add a new {@link Endpoint} constant instead.
public class HttpAction {

    private final ApiClient apiClient;
    private final Endpoint endpoint;
    private int expectedStatusCode;
    private Object body;
    private final Map<String, Object> pathParams = new LinkedHashMap<>();

    public HttpAction(ApiClient apiClient, Endpoint endpoint) {
        this.apiClient = apiClient;
        this.endpoint = endpoint;
        this.expectedStatusCode = endpoint.defaultStatusCode();
    }

    public HttpAction withBody(Object body) {
        this.body = body;
        return this;
    }

    public HttpAction withPathParam(String name, Object value) {
        pathParams.put(name, value);
        return this;
    }

    public HttpAction withExpectedStatusCode(int expectedStatusCode) {
        this.expectedStatusCode = expectedStatusCode;
        return this;
    }

    public Response perform() {
        RequestSpecification spec = apiClient.getRequestSpec();

        if (!pathParams.isEmpty()) {
            spec.pathParams(pathParams);
        }
        if (body != null) {
            spec.body(body);
        }

        Response response = switch (endpoint.method()) {
            case GET    -> spec.when().get(endpoint.path());
            case POST   -> spec.when().post(endpoint.path());
            case PUT    -> spec.when().put(endpoint.path());
            case PATCH  -> spec.when().patch(endpoint.path());
            case DELETE -> spec.when().delete(endpoint.path());
        };

        response.then().statusCode(expectedStatusCode);
        return response;
    }
}
