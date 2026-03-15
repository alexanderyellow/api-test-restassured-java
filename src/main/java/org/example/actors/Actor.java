package org.example.actors;

import io.restassured.response.Response;
import org.example.actions.AbstractAction;
import org.example.actions.LoginAction;
import org.example.clients.ApiClient;
import org.example.model.CredentialsDTO;
import org.example.model.LoginResponseDTO;
import org.example.model.Session;
import org.example.config.AppConfig;
import org.example.model.User;

import java.util.function.Function;

/// Represents a test actor that can perform actions using an API client.
public class Actor {
    private final ApiClient apiClient;
    private final CredentialsDTO credentials;
    private User user;
    private final Session session = new Session();

    public Actor(AppConfig appConfig, CredentialsDTO credentials) {
        this.credentials = credentials;
        this.apiClient = new ApiClient(appConfig.getBaseUrl(), session);
    }

    public Actor login() {
        Response response = perform(apiClient -> new LoginAction(apiClient, credentials));
        LoginResponseDTO loginResponse = response.as(LoginResponseDTO.class);
        user = loginResponse.user();
        session.setAccessToken(loginResponse.accessToken());

        return this;
    }

    public CredentialsDTO getCredentials() {
        return credentials;
    }

    public User getUser() {
        return user;
    }

    public Session getSession() {
        return session;
    }

    public <T extends AbstractAction> Response perform(Function<ApiClient, T> actionBuilder) {
        return actionBuilder.apply(apiClient).perform();
    }

}
