package org.example;

import org.assertj.core.api.Assertions;
import org.example.actions.Endpoints;
import org.example.data.PlayerTestDataFactory;
import org.example.model.response.PlayerResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DeleteOnePlayerTest extends BaseTest {

    private PlayerResponse expectedPlayer;

    @BeforeAll
    public void setUp() {
        expectedPlayer = admin
                .post(Endpoints.CREATE_PLAYER, PlayerTestDataFactory.validPlayerItem().build())
                .as(PlayerResponse.class);
    }

    @Test
    public void deleteOnePlayerTest() {
        admin.delete(Endpoints.DELETE_PLAYER, expectedPlayer.id());

        List<PlayerResponse> actualPlayersList = admin.get(Endpoints.GET_ALL_PLAYERS)
                .jsonPath()
                .getList("", PlayerResponse.class);

        Assertions.assertThat(actualPlayersList)
                .extracting(PlayerResponse::id)
                .as("All created players should be deleted and not present in the list")
                .doesNotContain(expectedPlayer.id());
    }
}
