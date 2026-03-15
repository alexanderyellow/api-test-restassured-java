package org.example.actions;

import io.restassured.response.Response;
import org.example.clients.ApiClient;
import org.example.model.PlayerRequestOneDTO;

/// Action to retrieve a player by email.
public class GetOnePlayerAction extends AbstractAction {
    private static final String END_POINT = "/api/automationTask/getOne";
    private static final int EXPECTED_STATUS_CODE = 201;

    private final PlayerRequestOneDTO playerRequestOneDTO;

    public GetOnePlayerAction(ApiClient apiClient, PlayerRequestOneDTO playerRequestOneDTO) {
        super(apiClient, EXPECTED_STATUS_CODE);
        this.playerRequestOneDTO = playerRequestOneDTO;
    }

    @Override
    public Response perform() {
        Response response = apiClient
                .getRequestSpec()
                .body(playerRequestOneDTO)
                .when()
                .post(END_POINT);

        response.then().statusCode(expectedStatusCode);

        return response;
    }
}
