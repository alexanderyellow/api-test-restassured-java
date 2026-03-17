package org.example;

import org.assertj.core.api.SoftAssertions;
import org.example.actions.CreatePlayerAction;
import org.example.actions.DeletePlayerAction;
import org.example.actions.GetAllPlayersAction;
import org.example.data.PlayerTestDataFactory;
import org.example.model.PlayerResponseDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class GetAllPlayersTest extends BaseTest {

    private final List<PlayerResponseDTO> createdPlayers = new CopyOnWriteArrayList<>();

    @BeforeAll
    public void setUp() {
        Stream.generate(() -> PlayerTestDataFactory.validPlayerItem().build())
                .limit(3)
                .forEach(playerBeingCreated -> {
                    PlayerResponseDTO playerResponseDTO = admin
                            .perform(apiClient -> new CreatePlayerAction(apiClient, playerBeingCreated))
                            .as(PlayerResponseDTO.class);
                    createdPlayers.add(playerResponseDTO);
                });
    }

    @Test
    public void getAllPlayersTest() {
        List<PlayerResponseDTO> actualSortedPlayersList = admin.perform(GetAllPlayersAction::new)
                .jsonPath()
                .getList("", PlayerResponseDTO.class)
                .stream()
                .sorted(Comparator.comparing(PlayerResponseDTO::name, Comparator.nullsFirst(Comparator.naturalOrder())))
                .toList();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualSortedPlayersList).as("Players list").isNotEmpty();
            softly.assertThat(actualSortedPlayersList).as("Sorted players list")
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields("passwordChange", "passwordRepeat", "v")
                    .containsAll(createdPlayers);
            softly.assertThat(actualSortedPlayersList).extracting(PlayerResponseDTO::id)
                    .as("Global player list for duplicates")
                    .doesNotHaveDuplicates();
        });
    }

    @AfterAll
    public void cleanUp() {
        createdPlayers
                .forEach(playerBeingDeleted ->
                        admin.perform(apiClient -> new DeletePlayerAction(apiClient, playerBeingDeleted.id()))
                );
    }
}
