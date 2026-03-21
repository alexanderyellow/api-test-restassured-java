package org.example;

import org.assertj.core.api.Assertions;
import org.example.actions.CreatePlayerAction;
import org.example.actions.DeletePlayerAction;
import org.example.actions.GetAllPlayersAction;
import org.example.data.PlayerTestDataFactory;
import org.example.model.PlayerRequestDTO;
import org.example.model.PlayerResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;


public class DeleteOnePlayerTest extends BaseTest {
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
    public void deleteOnePlayerTest() {
        admin.perform(apiClient -> new DeletePlayerAction(apiClient, expectedPlayer.id()));
        List<PlayerResponseDTO> actualPlayersList = admin.perform(GetAllPlayersAction::new)
                .jsonPath()
                .getList("", PlayerResponseDTO.class);

        Assertions.assertThat(actualPlayersList)
                .extracting(PlayerResponseDTO::id)
                .as("All created players should be deleted and not present in the list")
                .doesNotContain(expectedPlayer.id());
    }
}
