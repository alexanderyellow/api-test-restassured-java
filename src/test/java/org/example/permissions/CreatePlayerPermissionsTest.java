package org.example.permissions;

import org.example.actions.Endpoints;
import org.example.data.PlayerTestDataFactory;
import org.junit.jupiter.api.Test;

public class CreatePlayerPermissionsTest extends BasePermissionsTest {

    @Test
    public void createPlayerByNotAuthenticatedUserTest() {
        notAuthenticatedActor.post(Endpoints.CREATE_PLAYER, PlayerTestDataFactory.validPlayerItem().build(), 401);
    }
}
