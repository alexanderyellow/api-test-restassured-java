package org.example.actions;

import io.restassured.response.Response;
import org.example.clients.ApiClient;

/// Action to retrieve all players.
public class GetAllPlayersAction extends AbstractAction {
    private static final String END_POINT = "/automationTask/getAll";
    private static final int EXPECTED_STATUS_CODE = 200;

    public GetAllPlayersAction(ApiClient apiClient) {
        super(apiClient, EXPECTED_STATUS_CODE);
    }

    @Override
    public Response perform() {
        Response response = apiClient
                .getRequestSpec()
                .when()
                .get(END_POINT);

        response.then().statusCode(expectedStatusCode);

        return response;
    }
}
