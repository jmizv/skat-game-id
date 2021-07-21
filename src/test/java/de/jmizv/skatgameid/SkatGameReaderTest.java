package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class SkatGameReaderTest {

    @Test
    void test_read() throws IOException {
        SkatGameReader skatGameReader = new SkatGameReader();
        Game read = skatGameReader.read(new File(getClass().getResource("/sample_game").getFile()));
        assertThat(read).isNotNull();

    }
}
