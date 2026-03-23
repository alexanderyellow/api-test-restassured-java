package org.example;

import org.assertj.core.api.SoftAssertions;
import org.example.actions.Endpoints;
import org.example.data.PlayerTestDataFactory;
import org.example.model.request.CreatePlayerRequest;
import org.example.model.response.PlayerResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class CreatePlayerTest extends BaseTest {

    private final List<PlayerResponse> createdPlayers = new CopyOnWriteArrayList<>();

    private Stream<CreatePlayerRequest> playerDataProvider() {
        return Stream.generate(() -> PlayerTestDataFactory.validPlayerItem().build())
                .limit(12);
    }

    @ParameterizedTest
    @MethodSource("playerDataProvider")
    @Execution(ExecutionMode.CONCURRENT)
    public void createPlayerTest(CreatePlayerRequest playerRequestDTO) {
        PlayerResponse playerResponseDTO = admin
                .post(Endpoints.CREATE_PLAYER, playerRequestDTO)
                .as(PlayerResponse.class);

        createdPlayers.add(playerResponseDTO);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(playerResponseDTO.id()).as("ID").isNotNull();
            softly.assertThat(playerResponseDTO.username()).as("Username").isEqualTo(playerRequestDTO.getUsername());
            softly.assertThat(playerResponseDTO.email()).as("Email").isEqualTo(playerRequestDTO.getEmail());
            softly.assertThat(playerResponseDTO.name()).as("Name").isEqualTo(playerRequestDTO.getName());
            softly.assertThat(playerResponseDTO.surname()).as("Surname").isEqualTo(playerRequestDTO.getSurname());
            softly.assertThat(playerResponseDTO.currencyCode()).as("Currency Code").isEqualTo(playerRequestDTO.getCurrencyCode());
            softly.assertThat(playerResponseDTO.passwordChange()).as("Password Change").isEqualTo(playerRequestDTO.getPasswordChange());
            softly.assertThat(playerResponseDTO.passwordRepeat()).as("Password Repeat").isEqualTo(playerRequestDTO.getPasswordRepeat());
            softly.assertThat(playerResponseDTO.v()).as("Version").isNotNull();
        });
    }

    // Looks like a Bug. It is possible to create player with existence username and email.
    @Test
    public void createPlayerWithExistingEmailTest() {
        CreatePlayerRequest firstPlayerRequest = PlayerTestDataFactory.validPlayerItem().build();
        PlayerResponse firstPlayerResponse = admin
                .post(Endpoints.CREATE_PLAYER, firstPlayerRequest)
                .as(PlayerResponse.class);
        createdPlayers.add(firstPlayerResponse);

        CreatePlayerRequest secondPlayerRequest = PlayerTestDataFactory.validPlayerItem()
                .username(firstPlayerRequest.getUsername())
                .email(firstPlayerRequest.getEmail())
                .build();

        admin.post(Endpoints.CREATE_PLAYER, secondPlayerRequest, 400);
    }

    @AfterAll
    public void cleanUp() {
        createdPlayers.forEach(player -> admin.delete(Endpoints.DELETE_PLAYER, player.id()));
    }
}
