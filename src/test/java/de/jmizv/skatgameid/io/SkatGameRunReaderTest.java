package de.jmizv.skatgameid.io;

import de.jmizv.skatgameid.Card;
import de.jmizv.skatgameid.GameRun;
import de.jmizv.skatgameid.GameTypeKind;
import de.jmizv.skatgameid.Player;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class SkatGameRunReaderTest {

    @Test
    void shoud() throws IOException {
        String resourceName = "/gamerun/sample_game_run";
        URL resource = getClass().getResource(resourceName);
        assertThat(resource).isNotNull();
        SkatGameRunReader skatGameReader = new SkatGameRunReader();
        GameRun gameRun = skatGameReader.read(new File(resource.getFile()));
        String id = gameRun.computeId();
        assertThat(id).isNotNull();
        assertThat(gameRun.soloPlayer()).contains(Player.FOREHAND);
        assertThat(gameRun.bid(Player.FOREHAND)).isEqualTo(7);
        assertThat(gameRun.bid(Player.MIDHAND)).isEqualTo(5);
        assertThat(gameRun.bid(Player.REARHAND)).isEqualTo(4);
        assertThat(gameRun.gameType().gameTypeKind()).isEqualTo(GameTypeKind.CLUBS);
        assertThat(gameRun.gameType().isHand()).isTrue();
        assertThat(gameRun.gameType().isOuvert()).isTrue();
        assertThat(gameRun.gameType().isHandAnnounced()).isTrue();
        assertThat(gameRun.gameType().isOuvertAnnounced()).isTrue();
        assertThat(gameRun.skatCard(0)).isEqualTo(Card.of("R7"));
        assertThat(gameRun.skatCard(1)).isEqualTo(Card.of("R8"));
        assertThat(gameRun.trickAsCards(1)).isEqualTo(new Card[]{Card.of("S8"), Card.of("RX"), Card.of("GO")});
    }
}
