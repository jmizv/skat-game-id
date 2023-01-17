package de.jmizv.skatgameid;

import de.jmizv.skatgameid.io.SkatGameRunReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class GameRunTest {

    @Test
    void test() throws URISyntaxException, IOException {
        String resourceName = "/gamerun/sample_game_run";
        URL resource = getClass().getResource(resourceName);
        GameRun gameRun = new SkatGameRunReader().read(new File(resource.toURI()));
        String id = gameRun.computeId();
        assertThat(id).isNotEmpty();
    }
}
