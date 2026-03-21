package org.example.data;

import net.datafaker.Faker;
import org.example.model.request.CreatePlayerRequest;

public class PlayerTestDataFactory {
    private static final Faker faker = new Faker();

    public static CreatePlayerRequest.CreatePlayerRequestBuilder validPlayerItem() {
        String password = faker.credentials().password();
        String uniqueName = faker.regexify("[a-zA-Z0-9]{10}");

        return CreatePlayerRequest.builder()
                .currencyCode(faker.money().currency())
                .email(faker.internet().emailAddress(uniqueName))
                .name(faker.name().firstName())
                .passwordChange(password)
                .passwordRepeat(password)
                .surname(faker.name().lastName())
                .username(faker.credentials().username());
    }
}
