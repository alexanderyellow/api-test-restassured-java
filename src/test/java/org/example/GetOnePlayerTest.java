package org.example;

import io.restassured.response.Response;
import net.datafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.example.actions.Endpoints;
import org.example.data.PlayerTestDataFactory;
import org.example.model.request.CreatePlayerRequest;
import org.example.model.request.GetPlayerRequest;
import org.example.model.response.PlayerResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetOnePlayerTest extends BaseTest {

    private PlayerResponse expectedPlayer;

    @BeforeAll
    public void setUp() {
        CreatePlayerRequest playerRequestDTO = PlayerTestDataFactory.validPlayerItem().build();
        expectedPlayer = admin
                .post(Endpoints.CREATE_PLAYER, playerRequestDTO)
                .as(PlayerResponse.class);
    }

    @Test
    public void getOnePlayerTest() {
        GetPlayerRequest request = new GetPlayerRequest(expectedPlayer.email());
        PlayerResponse actualPlayer = admin
                .post(Endpoints.GET_ONE_PLAYER, request)
                .as(PlayerResponse.class);

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
        GetPlayerRequest request = new GetPlayerRequest(faker.internet().emailAddress());
        Response response = admin.post(Endpoints.GET_ONE_PLAYER, request, 400);

        assertThat(response.jsonPath().getString("path")).isEqualTo("/automationTask/getOne");
    }

    @AfterAll
    public void cleanUp() {
        admin.delete(Endpoints.DELETE_PLAYER, expectedPlayer.id());
    }
}
