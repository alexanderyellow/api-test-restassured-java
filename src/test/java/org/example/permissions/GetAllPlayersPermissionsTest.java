package org.example.permissions;

import org.example.actions.Endpoints;
import org.example.data.PlayerTestDataFactory;
import org.example.model.response.PlayerResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GetAllPlayersPermissionsTest extends BasePermissionsTest {

    private PlayerResponse expectedPlayer;

    @BeforeAll
    public void setUp() {
        expectedPlayer = admin
                .post(Endpoints.CREATE_PLAYER, PlayerTestDataFactory.validPlayerItem().build())
                .as(PlayerResponse.class);
    }

    @Test
    public void getAllPlayersByNotAuthenticatedUserTest() {
        notAuthenticatedActor.get(Endpoints.GET_ALL_PLAYERS, 401);
    }

    @AfterAll
    public void cleanUp() {
        admin.delete(Endpoints.DELETE_PLAYER, expectedPlayer.id());
    }
}
