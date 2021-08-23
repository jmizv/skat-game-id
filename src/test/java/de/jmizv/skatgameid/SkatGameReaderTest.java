package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class SkatGameReaderTest {

    @Test
    void test_read_default() throws IOException {
        test_read("/sample_game_default");
    }

    @Test
    void test_read_compact() throws IOException {
        test_read("/sample_game_compact");
    }

    @Test
    void test_read_expanded() throws IOException {
        test_read("/sample_game_expanded");
    }

    private void test_read(String resourceName) throws IOException {
        SkatGameReader skatGameReader = new SkatGameReader();
        URL resource = getClass().getResource(resourceName);
        assertThat(resource).isNotNull();
        Game readGame = skatGameReader.read(new File(resource.getFile()));
        assertThat(readGame).isNotNull();
        assertThat(readGame.getPlayerFront().get(0)).isEqualTo(Card.of("S7"));
        assertThat(readGame.getPlayerMiddle().get(0)).isEqualTo(Card.of("R9"));
        assertThat(readGame.getPlayerRear().get(0)).isEqualTo(Card.of("GU"));
        assertThat(readGame.getSkat().get(0)).isEqualTo(Card.of("EK"));
    }
}