package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class SkatGameWriterTest {

    @Test
    void should_write() throws IOException {
        Game random = Game.random(0);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new SkatGameWriter().write(random, outputStream);
        String result = outputStream.toString();
        assertThat(result).isNotEmpty();
        Game readGame = new SkatGameReader().read(new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8)));
        assertThat(readGame).isEqualTo(random);
    }

}
