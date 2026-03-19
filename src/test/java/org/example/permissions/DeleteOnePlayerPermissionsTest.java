package org.example.permissions;

import org.example.actions.CreatePlayerAction;
import org.example.actions.DeletePlayerAction;
import org.example.data.PlayerTestDataFactory;
import org.example.model.PlayerRequestDTO;
import org.example.model.PlayerResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DeleteOnePlayerPermissionsTest extends BasePermissionsTest {
    private PlayerResponseDTO playerBeingDeleted;

    @BeforeAll
    public void setUp() {
        PlayerRequestDTO playerRequestDTO = PlayerTestDataFactory.validPlayerItem().build();
        playerBeingDeleted = admin.perform(apiClient ->
                        new CreatePlayerAction(apiClient, playerRequestDTO)
                )
                .as(PlayerResponseDTO.class);
    }

    @Test
    public void deleteOnePlayerByNotAuthenticatedUserTest() {
        notAuthenticatedActor.perform(apiClient ->
                new DeletePlayerAction(apiClient, playerBeingDeleted.id())
                        .withExpectedStatusCode(401)
        );
    }
}
