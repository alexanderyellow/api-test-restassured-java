package org.example.get_all_players_test;

import org.example.BaseTest;

public class GetAllPlayersPositiveTest extends BaseTest {
//    private final List<PlayerItem> createdPlayers = new ArrayList<>();
//
//    @BeforeClass
//    public void setUp() {
//        for (int i = 0; i < 3; i++) {
//            PlayerItem playerToCreate = PlayerTestDataFactory.validPlayerItem().build();
//            Response response = supervisorActor
//                    .perform(apiClient ->
//                            new CreatePlayerAction(apiClient, playerToCreate, supervisorActor.getLogin())
//                    );
//            PlayerCreateResponseDto createdPlayerDto = response.as(PlayerCreateResponseDto.class);
//            playerToCreate.setId(createdPlayerDto.id());
//
//            createdPlayers.add(playerToCreate);
//        }
//    }
//
//    @Test
//    public void getAllPlayersBySupervisorTest() {
//        Response response = supervisorActor
//                .perform(GetAllPlayersAction::new);
//
//        PlayerGetAllResponseDto getAllResponse = response.as(PlayerGetAllResponseDto.class);
//        List<PlayerItem> players = getAllResponse.players();
//
//        List<Integer> playerIds = players.stream()
//                .map(PlayerItem::getId)
//                .toList();
//        long distinctIdsCount = playerIds.stream().distinct().count();
//        Assert.assertEquals(distinctIdsCount, playerIds.size(), "Duplicate player IDs found in the list: " + playerIds);
//
//        createdPlayers.forEach(createdPlayer -> {
//            PlayerItem foundPlayer = players.stream()
//                    .filter(p -> p.getId().equals(createdPlayer.getId()))
//                    .findFirst()
//                    .orElseThrow(() -> new AssertionError("Created player with ID " + createdPlayer.getId() + " not found in all players list"));
//
//            Assert.assertEquals(foundPlayer.getScreenName(), createdPlayer.getScreenName(), "Screen name mismatch for player ID " + createdPlayer.getId());
//            Assert.assertEquals(foundPlayer.getGender(), createdPlayer.getGender(), "Gender mismatch for player ID " + createdPlayer.getId());
//            Assert.assertEquals(foundPlayer.getAge(), createdPlayer.getAge(), "Age mismatch for player ID " + createdPlayer.getId());
//        });
//    }
}
