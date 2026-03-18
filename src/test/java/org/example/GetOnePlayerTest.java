package org.example;

import io.restassured.response.Response;
import net.datafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.example.actions.CreatePlayerAction;
import org.example.actions.DeletePlayerAction;
import org.example.actions.GetOnePlayerAction;
import org.example.data.PlayerTestDataFactory;
import org.example.model.PlayerRequestDTO;
import org.example.model.PlayerRequestOneDTO;
import org.example.model.PlayerResponseDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetOnePlayerTest extends BaseTest {

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
    public void getOnePlayerTest() {
        PlayerRequestOneDTO request = new PlayerRequestOneDTO(expectedPlayer.email());
        PlayerResponseDTO actualPlayer = admin
                .perform(apiClient -> new GetOnePlayerAction(apiClient, request))
                .as(PlayerResponseDTO.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actualPlayer.id()).as("ID").isEqualTo(expectedPlayer.id());
            softly.assertThat(actualPlayer.username()).as("Username").isEqualTo(expectedPlayer.username());
            softly.assertThat(actualPlayer.email()).as("Email").isEqualTo(expectedPlayer.email());
            softly.assertThat(actualPlayer.name()).as("Name").isEqualTo(expectedPlayer.name());
            softly.assertThat(actualPlayer.surname()).as("Surname").isEqualTo(expectedPlayer.surname());
            softly.assertThat(actualPlayer.currencyCode()).as("Currency Code").isEqualTo(expectedPlayer.currencyCode());
        });
    }

    @Test
    public void getOnePlayerNotFoundTest() {
        Faker faker = new Faker();
        PlayerRequestOneDTO request = new PlayerRequestOneDTO(faker.internet().emailAddress());
        Response response = admin.perform(apiClient ->
                new GetOnePlayerAction(apiClient, request)
                        .withExpectedStatusCode(400)
        );

        assertThat(response.jsonPath().getString("path")).isEqualTo("/automationTask/getOne");
    }

    @AfterAll
    public void cleanUp() {
        admin.perform(apiClient -> new DeletePlayerAction(apiClient, expectedPlayer.id()));
    }
}
