package org.example.actions;

import io.restassured.response.Response;
import org.example.clients.ApiClient;

/// Action to delete a player by id.
public class DeletePlayerAction extends AbstractAction {
    private static final String END_POINT = "/automationTask/deleteOne/%s";
    private static final int EXPECTED_STATUS_CODE = 200;

    private final String playerId;

    public DeletePlayerAction(ApiClient apiClient, String playerId) {
        super(apiClient, EXPECTED_STATUS_CODE);
        this.playerId = playerId;
    }

    @Override
    public Response perform() {
        Response response = apiClient
                .getRequestSpec()
                .when()
                .delete(String.format(END_POINT, playerId));

        response.then().statusCode(expectedStatusCode);

        return response;
    }
}
