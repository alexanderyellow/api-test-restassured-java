package org.example.actions;

import io.restassured.response.Response;
import org.example.clients.ApiClient;
import org.example.model.CredentialsDTO;

public class LoginAction extends AbstractAction {
    private static final String END_POINT = "/api/tester/login";
    private static final int EXPECTED_STATUS_CODE = 201;

    private final CredentialsDTO credentials;

    public LoginAction(ApiClient client, CredentialsDTO credentials) {
        super(client, EXPECTED_STATUS_CODE);
        this.credentials = credentials;
    }

    @Override
    public Response perform() {
        Response response = apiClient
                .getRequestSpec()
                .body(credentials)
                .when()
                .post(END_POINT);

        response.then().statusCode(expectedStatusCode);

        return response;
    }
}
