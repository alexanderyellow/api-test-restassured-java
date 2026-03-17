package org.example;

import io.restassured.response.Response;
import net.datafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.example.actions.LoginAction;
import org.example.actors.Actor;
import org.example.model.CredentialsDTO;
import org.example.model.LoginResponseDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginTest extends BaseTest {

    @Test
    public void successLoginTest() {
        CredentialsDTO credentials = new CredentialsDTO(config.getUserEmail(), config.getUserPassword());
        Actor correctActor = new Actor(config, credentials);

        Response response = correctActor.perform(apiClient -> new LoginAction(apiClient, credentials));
        LoginResponseDTO loginResponse = response.as(LoginResponseDTO.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(loginResponse.accessToken()).isNotEmpty();
            softly.assertThat(loginResponse.user().email()).isEqualTo(config.getUserEmail());
        });
    }

    @Test
    public void failureLoginTest() {
        Faker faker = new Faker();
        CredentialsDTO credentials = new CredentialsDTO(faker.internet().emailAddress(), faker.credentials().password());
        Actor incorrectActor = new Actor(config, credentials);

        Response response = incorrectActor.perform(apiClient ->
                new LoginAction(apiClient, credentials)
                        .withExpectedStatusCode(401)
        );

        assertThat(response.jsonPath().getString("message"))
                .isEqualTo("Email or password is incorrect");
    }

}
