package org.example;

import io.restassured.response.Response;
import org.example.actions.CreatePlayerAction;
import org.example.actions.DeletePlayerAction;
import org.example.actions.GetAllPlayersAction;
import org.example.actions.GetOnePlayerAction;
import org.example.data.PlayerTestDataFactory;
import org.example.model.PlayerRequestDTO;
import org.example.model.PlayerRequestOneDTO;
import org.example.model.PlayerResponseDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.assertj.core.api.SoftAssertions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemoTest extends BaseTest {

    private final List<PlayerResponseDTO> createdPlayers = new ArrayList<>();

    private static Stream<PlayerRequestDTO> playerDataProvider() {
        return Stream.generate(() -> PlayerTestDataFactory.validPlayerItem().build())
                .limit(12);
    }

    private Stream<PlayerResponseDTO> createdPlayersProvider() {
        return createdPlayers.stream();
    }

    @Order(1)
    @ParameterizedTest
    @MethodSource("playerDataProvider")
    public void createPlayerTest(PlayerRequestDTO playerRequestDTO) {
        Response response = userActor.perform(apiClient -> new CreatePlayerAction(apiClient, playerRequestDTO));
        PlayerResponseDTO playerResponseDTO = response.as(PlayerResponseDTO.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(playerResponseDTO.id()).as("ID").isNotNull();
            softly.assertThat(playerResponseDTO.username()).as("Username").isEqualTo(playerRequestDTO.getUsername());
            softly.assertThat(playerResponseDTO.email()).as("Email").isEqualTo(playerRequestDTO.getEmail());
            softly.assertThat(playerResponseDTO.name()).as("Name").isEqualTo(playerRequestDTO.getName());
            softly.assertThat(playerResponseDTO.surname()).as("Surname").isEqualTo(playerRequestDTO.getSurname());
            softly.assertThat(playerResponseDTO.currencyCode()).as("Currency Code").isEqualTo(playerRequestDTO.getCurrencyCode());
            softly.assertThat(playerResponseDTO.passwordChange()).as("Password Change").isEqualTo(playerRequestDTO.getPasswordChange());
            softly.assertThat(playerResponseDTO.passwordRepeat()).as("Password Repeat").isEqualTo(playerRequestDTO.getPasswordRepeat());
            softly.assertThat(playerResponseDTO.v()).as("Version (__v)").isNotNull();
        });

        createdPlayers.add(playerResponseDTO);
    }

    @Order(2)
    @ParameterizedTest
    @MethodSource("createdPlayersProvider")
    public void getOnePlayerTest(PlayerResponseDTO expectedPlayer) {
        PlayerRequestOneDTO request = new PlayerRequestOneDTO(expectedPlayer.email());
        Response response = userActor.perform(apiClient -> new GetOnePlayerAction(apiClient, request));
        PlayerResponseDTO actualPlayer = response.as(PlayerResponseDTO.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualPlayer.id()).as("ID").isEqualTo(expectedPlayer.id());
            softly.assertThat(actualPlayer.username()).as("Username").isEqualTo(expectedPlayer.username());
            softly.assertThat(actualPlayer.email()).as("Email").isEqualTo(expectedPlayer.email());
            softly.assertThat(actualPlayer.name()).as("Name").isEqualTo(expectedPlayer.name());
            softly.assertThat(actualPlayer.surname()).as("Surname").isEqualTo(expectedPlayer.surname());
            softly.assertThat(actualPlayer.currencyCode()).as("Currency Code").isEqualTo(expectedPlayer.currencyCode());
        });
    }

    @Order(3)
    @Test
    public void getAllPlayersTest() {
        Response response = userActor.perform(GetAllPlayersAction::new);
        List<PlayerResponseDTO> players = response.jsonPath().getList("", PlayerResponseDTO.class);

        List<PlayerResponseDTO> sortedPlayers = players.stream()
                .sorted(Comparator.comparing(PlayerResponseDTO::name, Comparator.nullsFirst(Comparator.naturalOrder())))
                .toList();

        List<String> createdPlayerIds = createdPlayers.stream()
                .map(PlayerResponseDTO::id)
                .toList();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(players).as("Players list").isNotEmpty();
            softly.assertThat(players).as("Sorted players list").containsAll(sortedPlayers);

            List<String> allPlayerIds = players.stream().map(PlayerResponseDTO::id).toList();
            softly.assertThat(allPlayerIds).as("Global player list for duplicates").doesNotHaveDuplicates();

            softly.assertThat(players).extracting(PlayerResponseDTO::id)
                    .as("Created player IDs in the list")
                    .containsAll(createdPlayerIds);
        });
    }

    @Order(4)
    @ParameterizedTest
    @MethodSource("createdPlayersProvider")
    public void deleteOnePlayerTest(PlayerResponseDTO playerBeingDeleted) {
        Response deleteResponse = userActor.perform(apiClient -> new DeletePlayerAction(apiClient, playerBeingDeleted.id()));

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(deleteResponse.statusCode()).as("Delete status code").isEqualTo(200);

            // Verify player is gone
            PlayerRequestOneDTO getRequest = new PlayerRequestOneDTO(playerBeingDeleted.email());
            Response getResponse = userActor.perform(apiClient ->
                    new GetOnePlayerAction(apiClient, getRequest).withExpectedStatusCode(400));
            softly.assertThat(getResponse.statusCode()).as("Get status code after delete").isEqualTo(400);
        });
    }

    @Order(5)
    @Test
    public void allPlayersAreGoneTest() {
        Response response = userActor.perform(GetAllPlayersAction::new);
        List<PlayerResponseDTO> players = response.jsonPath().getList("", PlayerResponseDTO.class);

        List<String> createdPlayerIds = createdPlayers.stream()
                .map(PlayerResponseDTO::id)
                .toList();

        SoftAssertions.assertSoftly(softly ->
                softly.assertThat(players).extracting(PlayerResponseDTO::id)
                        .as("Deleted player IDs should not be in the list")
                        .doesNotContainAnyElementsOf(createdPlayerIds)
        );
    }
}
