package org.example.actors;

import io.restassured.response.Response;
import org.example.actions.Endpoint;
import org.example.actions.Endpoints;
import org.example.actions.HttpAction;
import org.example.clients.ApiClient;
import org.example.clients.Session;
import org.example.model.request.LoginRequest;
import org.example.model.response.LoginResponse;
import org.example.model.response.User;

import java.util.function.Function;

/// Represents a test actor that can perform API actions using typed convenience methods
/// or a raw {@link #perform(Function)} escape hatch for custom configurations.
public class Actor {

    private final ApiClient apiClient;
    private final LoginRequest credentials;
    private User user;
    private final Session session = new Session();

    public Actor(LoginRequest credentials) {
        this.credentials = credentials;
        this.apiClient = new ApiClient(session);
    }

    // ── Auth ──────────────────────────────────────────────────────────────────

    public Actor login() {
        Response response = post(Endpoints.LOGIN, credentials);
        LoginResponse loginResponse = response.as(LoginResponse.class);
        user = loginResponse.user();
        session.setAccessToken(loginResponse.accessToken());
        
        return this;
    }

    // ── Convenience methods ───────────────────────────────────────────────────

    /// Performs a GET to the given endpoint and asserts the default status code.
    public Response get(Endpoint endpoint) {
        return perform(client -> new HttpAction(client, endpoint));
    }

    /// Performs a GET and asserts {@code expectedStatusCode} instead of the endpoint default.
    public Response get(Endpoint endpoint, int expectedStatusCode) {
        return perform(client -> new HttpAction(client, endpoint).withExpectedStatusCode(expectedStatusCode));
    }

    /// Performs a POST with {@code body} and asserts the default status code.
    public Response post(Endpoint endpoint, Object body) {
        return perform(client -> new HttpAction(client, endpoint).withBody(body));
    }

    /// Performs a POST with {@code body} and asserts {@code expectedStatusCode}.
    public Response post(Endpoint endpoint, Object body, int expectedStatusCode) {
        return perform(client -> new HttpAction(client, endpoint).withBody(body).withExpectedStatusCode(expectedStatusCode));
    }

    /// Performs a DELETE where {@code id} is bound to the {@code {id}} path parameter.
    public Response delete(Endpoint endpoint, String id) {
        return perform(client -> new HttpAction(client, endpoint).withPathParam("id", id));
    }

    /// Performs a DELETE and asserts {@code expectedStatusCode} instead of the endpoint default.
    public Response delete(Endpoint endpoint, String id, int expectedStatusCode) {
        return perform(client -> new HttpAction(client, endpoint).withPathParam("id", id).withExpectedStatusCode(expectedStatusCode));
    }

    // ── Escape hatch for fully custom action configuration ────────────────────

    /// Applies {@code actionBuilder} to this actor's {@link ApiClient} and executes the result.
    /// Use when the convenience methods above do not cover the needed configuration.
    public Response perform(Function<ApiClient, HttpAction> actionBuilder) {
        return actionBuilder.apply(apiClient).perform();
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    public LoginRequest getCredentials() {
        return credentials;
    }

    public User getUser() {
        return user;
    }

    public Session getSession() {
        return session;
    }
}
