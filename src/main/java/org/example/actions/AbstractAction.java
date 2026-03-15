package org.example.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.example.clients.ApiClient;

/// Base class for all API actions.
public abstract class AbstractAction {
    protected final ApiClient apiClient;
    protected int expectedStatusCode;
    protected final ObjectMapper objectMapper;

    public AbstractAction(ApiClient apiClient, int expectedStatusCode) {
        this.apiClient = apiClient;
        this.expectedStatusCode = expectedStatusCode;
        objectMapper = new ObjectMapper();
    }

    public AbstractAction withExpectedStatusCode(int expectedStatusCode) {
        this.expectedStatusCode = expectedStatusCode;
        return this;
    }

    public abstract Response perform();
}
