package de.jmizv.skatgameid.io;

import de.jmizv.skatgameid.Card;
import de.jmizv.skatgameid.Game;
import de.jmizv.skatgameid.Util;

import java.io.*;
import java.nio.file.Files;

public class SkatGameReader {

    public Game read(File file) throws IOException {
        return read(Files.newInputStream(file.toPath()));
    }

    public Game read(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return read(reader);
        }
    }

    public Game read(BufferedReader reader) throws IOException {
        Game.Builder gameBuilder = Game.builder();
        String line;
        while (gameBuilder.cardsAdded() < 32) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            line = Util.removeCommentAndNormalize(line);
            if (line.length() % 2 != 0) {
                throw new IllegalArgumentException("Line has illegal format: " + line);
            }
            for (int i = 0; i < line.length() && gameBuilder.cardsAdded() < 32; i += 2) {
                gameBuilder.addCard(Card.of(line.substring(i, i + 2)));
            }
        }
        return gameBuilder.build();
    }
}
