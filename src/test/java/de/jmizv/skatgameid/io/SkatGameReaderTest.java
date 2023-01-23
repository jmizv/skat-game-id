package de.jmizv.skatgameid.io;

import de.jmizv.skatgameid.Card;
import de.jmizv.skatgameid.Game;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class SkatGameReaderTest {

    @ParameterizedTest
    @ValueSource(strings = {"/game/sample_game_default", "/game/sample_game_compact", "/game/sample_game_expanded"})
    void test_read_default(String resourceName) throws IOException {
        test_read(resourceName);
    }

    private void test_read(String resourceName) throws IOException {
        SkatGameReader skatGameReader = new SkatGameReader();
        URL resource = getClass().getResource(resourceName);
        assertThat(resource).isNotNull();
        Game readGame = skatGameReader.read(new File(resource.getFile()));
        assertThat(readGame).isNotNull();
        assertThat(readGame.frontCards().get(0)).isEqualTo(Card.of("S7"));
        assertThat(readGame.middleCards().get(0)).isEqualTo(Card.of("R9"));
        assertThat(readGame.rearCards().get(0)).isEqualTo(Card.of("GU"));
        assertThat(readGame.skatCards().get(0)).isEqualTo(Card.of("EK"));
    }
}