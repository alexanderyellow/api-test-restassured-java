package org.example.permissions;

import org.example.actions.CreatePlayerAction;
import org.example.data.PlayerTestDataFactory;
import org.example.model.PlayerRequestDTO;
import org.junit.jupiter.api.Test;

public class CreatePlayerPermissionsTest extends BasePermissionsTest {

    private final PlayerRequestDTO playerBeingCreated = PlayerTestDataFactory.validPlayerItem().build();

    @Test
    public void createPlayerByNotAuthenticatedUserTest() {
        notAuthenticatedActor.perform(apiClient ->
                new CreatePlayerAction(apiClient, playerBeingCreated)
                        .withExpectedStatusCode(401)
        );
    }
}
