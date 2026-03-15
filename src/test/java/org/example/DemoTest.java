package org.example;

import org.example.actions.CreatePlayerAction;
import org.example.data.PlayerTestDataFactory;
import org.example.model.PlayerRequestDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class DemoTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("playerDataProvider")
    public void createPlayerTest(PlayerRequestDTO playerRequestDTO) {
        userActor.perform(apiClient -> new CreatePlayerAction(apiClient, playerRequestDTO));
    }

    private static Stream<PlayerRequestDTO> playerDataProvider() {
        return Stream.generate(() -> PlayerTestDataFactory.validPlayerItem().build())
                .limit(1);
    }
}
