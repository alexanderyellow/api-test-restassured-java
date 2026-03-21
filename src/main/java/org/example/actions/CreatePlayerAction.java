package org.example.actions;

import io.restassured.response.Response;
import org.example.clients.ApiClient;
import org.example.model.PlayerRequestDTO;


/// Action to create a new player.
public class CreatePlayerAction extends AbstractAction {
    private static final String END_POINT = "/automationTask/create";
    private static final int EXPECTED_STATUS_CODE = 201;

    private final PlayerRequestDTO playerBeingCreated;

    public CreatePlayerAction(ApiClient apiClient, PlayerRequestDTO playerBeingCreated) {
        super(apiClient, EXPECTED_STATUS_CODE);
        this.playerBeingCreated = playerBeingCreated;
    }

    @Override
    public Response perform() {
        Response response = apiClient
                .getRequestSpec()
                .body(playerBeingCreated)
                .when()
                .post(END_POINT);

        response.then().statusCode(expectedStatusCode);

        return response;
    }
}
