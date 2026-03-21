package org.example;

import org.assertj.core.api.SoftAssertions;
import org.example.actions.Endpoints;
import org.example.data.PlayerTestDataFactory;
import org.example.model.response.PlayerResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class GetAllPlayersTest extends BaseTest {

    private final List<PlayerResponse> createdPlayers = new CopyOnWriteArrayList<>();

    @BeforeAll
    public void setUp() {
        Stream.generate(() -> PlayerTestDataFactory.validPlayerItem().build())
                .limit(3)
                .forEach(playerBeingCreated -> {
                    PlayerResponse playerResponseDTO = admin
                            .post(Endpoints.CREATE_PLAYER, playerBeingCreated)
                            .as(PlayerResponse.class);
                    createdPlayers.add(playerResponseDTO);
                });
    }

    @Test
    public void getAllPlayersTest() {
        List<PlayerResponse> actualSortedPlayersList = admin.get(Endpoints.GET_ALL_PLAYERS)
                .jsonPath()
                .getList("", PlayerResponse.class)
                .stream()
                .sorted(Comparator.comparing(PlayerResponse::name, Comparator.nullsFirst(Comparator.naturalOrder())))
                .toList();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualSortedPlayersList).as("Players list").isNotEmpty();
            softly.assertThat(actualSortedPlayersList).as("Sorted players list")
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields("passwordChange", "passwordRepeat", "v")
                    .containsAll(createdPlayers);
            softly.assertThat(actualSortedPlayersList).extracting(PlayerResponse::id)
                    .as("Global player list for duplicates")
                    .doesNotHaveDuplicates();
        });
    }

    @AfterAll
    public void cleanUp() {
        createdPlayers.forEach(player -> admin.delete(Endpoints.DELETE_PLAYER, player.id()));
    }
}
