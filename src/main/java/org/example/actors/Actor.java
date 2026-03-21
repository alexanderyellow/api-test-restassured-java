package org.example.actors;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
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
    @Getter
    private final LoginRequest credentials;
    @Getter
    private User user;
    @Getter
    private final Session session = new Session();

    public Actor(LoginRequest credentials) {
        this.credentials = credentials;
        this.apiClient = new ApiClient(session);
    }

    public Actor login() {
        Response response = post(Endpoints.LOGIN, credentials);
        LoginResponse loginResponse = response.as(LoginResponse.class);
        user = loginResponse.user();
        session.setAccessToken(loginResponse.accessToken());
        
        return this;
    }

    @Step("Get {endpoint}")
    public Response get(Endpoint endpoint) {
        return perform(client -> new HttpAction(client, endpoint));
    }

    @Step("Get {endpoint} with expected status code {expectedStatusCode}")
    public Response get(Endpoint endpoint, int expectedStatusCode) {
        return perform(client -> new HttpAction(client, endpoint).withExpectedStatusCode(expectedStatusCode));
    }

    @Step("Post {endpoint} with body {body}")
    public Response post(Endpoint endpoint, Object body) {
        return perform(client -> new HttpAction(client, endpoint).withBody(body));
    }

    @Step("Post {endpoint} with body {body} and expected status code {expectedStatusCode}")
    public Response post(Endpoint endpoint, Object body, int expectedStatusCode) {
        return perform(client -> new HttpAction(client, endpoint).withBody(body).withExpectedStatusCode(expectedStatusCode));
    }

    @Step("Delete {endpoint} with path param {id}")
    public Response delete(Endpoint endpoint, String id) {
        return perform(client -> new HttpAction(client, endpoint).withPathParam("id", id));
    }

    @Step("Delete {endpoint} with path param {id} and expected status code {expectedStatusCode}")
    public Response delete(Endpoint endpoint, String id, int expectedStatusCode) {
        return perform(client -> new HttpAction(client, endpoint).withPathParam("id", id).withExpectedStatusCode(expectedStatusCode));
    }

    public Response perform(Function<ApiClient, HttpAction> actionBuilder) {
        return actionBuilder.apply(apiClient).perform();
    }
}
