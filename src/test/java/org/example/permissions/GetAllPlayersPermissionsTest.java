package org.example.permissions;

import org.example.actions.CreatePlayerAction;
import org.example.actions.DeletePlayerAction;
import org.example.actions.GetAllPlayersAction;
import org.example.data.PlayerTestDataFactory;
import org.example.model.PlayerRequestDTO;
import org.example.model.PlayerResponseDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GetAllPlayersPermissionsTest extends BasePermissionsTest {
    private PlayerResponseDTO expectedPlayer;

    @BeforeAll
    public void setUp() {
        PlayerRequestDTO playerRequestDTO = PlayerTestDataFactory.validPlayerItem().build();
        expectedPlayer = admin.perform(apiClient ->
                        new CreatePlayerAction(apiClient, playerRequestDTO)
                )
                .as(PlayerResponseDTO.class);
    }

    @Test
    public void getAllPlayersByNotAuthenticatedUserTest() {
        notAuthenticatedActor.perform(apiClient ->
                new GetAllPlayersAction(apiClient)
                        .withExpectedStatusCode(401)
        );
    }

    @AfterAll
    public void cleanUp() {
        admin.perform(apiClient -> new DeletePlayerAction(apiClient, expectedPlayer.id()));
    }
}
