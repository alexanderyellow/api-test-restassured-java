package org.example.data;

import net.datafaker.Faker;
import org.example.model.PlayerRequestDTO;

public class PlayerTestDataFactory {
    private static final Faker faker = new Faker();

    public static PlayerRequestDTO.Builder validPlayerItem() {
        String password = faker.credentials().password();
        Long uniqueString = System.currentTimeMillis();

        return PlayerRequestDTO.builder()
                .currencyCode(faker.money().currency())
                .email(faker.internet().emailAddress(uniqueString.toString()))
                .name(faker.name().firstName())
                .passwordChange(password)
                .passwordRepeat(password)
                .surname(faker.name().lastName())
                .username(faker.credentials().username());
    }
}
