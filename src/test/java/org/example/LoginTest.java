package org.example;

import io.restassured.response.Response;
import net.datafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.example.actions.Endpoints;
import org.example.actors.Actor;
import org.example.model.request.LoginRequest;
import org.example.model.response.LoginResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginTest extends BaseTest {

    @Test
    public void successLoginTest() {
        LoginRequest credentials = new LoginRequest(config.auth().email(), config.auth().password());
        Actor correctActor = new Actor(credentials);

        Response response = correctActor.post(Endpoints.LOGIN, credentials);
        LoginResponse loginResponse = response.as(LoginResponse.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(loginResponse.accessToken()).isNotEmpty();
            softly.assertThat(loginResponse.user().email()).isEqualTo(config.auth().email());
        });
    }

    @Test
    public void failureLoginTest() {
        Faker faker = new Faker();
        LoginRequest credentials = new LoginRequest(faker.internet().emailAddress(), faker.credentials().password());
        Actor incorrectActor = new Actor(credentials);

        Response response = incorrectActor.post(Endpoints.LOGIN, credentials, 401);

        assertThat(response.jsonPath().getString("message"))
                .isEqualTo("Email or password is incorrect");
    }
}
