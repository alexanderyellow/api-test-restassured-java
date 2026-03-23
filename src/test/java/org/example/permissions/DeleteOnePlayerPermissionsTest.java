package org.example.permissions;

import org.example.actions.Endpoints;
import org.example.data.PlayerTestDataFactory;
import org.example.model.response.PlayerResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DeleteOnePlayerPermissionsTest extends BasePermissionsTest {

    private PlayerResponse playerBeingDeleted;

    @BeforeAll
    public void setUp() {
        playerBeingDeleted = admin
                .post(Endpoints.CREATE_PLAYER, PlayerTestDataFactory.validPlayerItem().build())
                .as(PlayerResponse.class);
    }

    @Test
    public void deleteOnePlayerByNotAuthenticatedUserTest() {
        notAuthenticatedActor.delete(Endpoints.DELETE_PLAYER, playerBeingDeleted.id(), 401);
    }
}
