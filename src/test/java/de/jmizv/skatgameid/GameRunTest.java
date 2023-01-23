package de.jmizv.skatgameid;

import de.jmizv.skatgameid.io.SkatGameRunReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class GameRunTest {

    @Test
    void generate_from_id_should_be_equal_to_source() throws Exception {
        GameRun gameRun = read();
        String id = gameRun.computeId();
        assertThat(id).isNotEmpty();
        assertThatCode(gameRun::computeIdAsBigInteger).doesNotThrowAnyException();
        GameRun gameRun1 = GameRun.ofId(id);
        assertThat(gameRun1).isEqualTo(gameRun);
    }

    @ParameterizedTest
    @MethodSource("bids")
    void should_compute_solo_player(int[] bids, Player expectedSoloPlayer) {
        GameRun read = new GameRun(Game.random(0),
                bids,
                new Card[]{Card.of("S7"), Card.of("S8")},
                GameType.of(GameTypeKind.SPADES),
                new int[][]{});
        Optional<Player> player = read.soloPlayer();
        if (expectedSoloPlayer == null) {
            assertThat(player).isEmpty();
        } else {
            assertThat(player).contains(expectedSoloPlayer);
        }
    }

    static Stream<Arguments> bids() {
        return Stream.of(
                Arguments.of(new int[]{0, 0, 0}, null),
                Arguments.of(new int[]{1, 0, 0}, Player.FOREHAND),
                Arguments.of(new int[]{3, 2, 0}, Player.FOREHAND),
                Arguments.of(new int[]{5, 2, 1}, Player.FOREHAND),
                Arguments.of(new int[]{5, 2, 4}, Player.FOREHAND),

                Arguments.of(new int[]{0, 1, 0}, Player.MIDHAND),
                Arguments.of(new int[]{1, 3, 0}, Player.MIDHAND),
                Arguments.of(new int[]{0, 5, 3}, Player.MIDHAND),
                Arguments.of(new int[]{2, 6, 1}, Player.MIDHAND),
                Arguments.of(new int[]{3, 7, 4}, Player.MIDHAND),

                Arguments.of(new int[]{0, 0, 1}, Player.REARHAND),
                Arguments.of(new int[]{3, 0, 4}, Player.REARHAND),
                Arguments.of(new int[]{0, 4, 5}, Player.REARHAND),
                Arguments.of(new int[]{5, 2, 6}, Player.REARHAND),
                Arguments.of(new int[]{2, 6, 7}, Player.REARHAND)
        );
    }

    @Test
    void should_get_array_of_bid_values() {
        int[] bids = GameRun.bidValues();
        assertThat(bids).hasSize(73);
        assertThat(bids[0]).isEqualTo(0);
        assertThat(bids[1]).isEqualTo(18);
        assertThat(bids[72]).isEqualTo(264);
        // check that we can't mutate the array.
        bids[1] = 81;
        assertThat(GameRun.bidValues()[1]).isEqualTo(18);
    }

    private static GameRun read() throws Exception {
        String resourceName = "/gamerun/sample_game_run";
        URL resource = GameRunTest.class.getResource(resourceName);
        return new SkatGameRunReader().read(new File(resource.toURI()));
    }
}
