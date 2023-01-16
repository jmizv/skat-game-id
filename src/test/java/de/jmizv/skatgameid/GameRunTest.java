package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameRunTest {

    @Test
    void test() {
        GameRun gameRun = new GameRun(Game.random(200),
                GameType.of(GameTypeKind.GRAND),
                new int[]{1, 0, 0},
                new Card[]{Card.of("R7"), Card.of("S9")},
                new int[1][]);
        String id = gameRun.computeId();
        assertThat(id).isNotEmpty();
    }
}
