package de.jmizv.skatgameid;

import java.io.*;

public class SkatGameReader {

    public Game read(File file) throws IOException {
        return read(new FileInputStream(file));
    }

    private static String normalize(String input) {
        return input.replace(" ", "")
                .replace("\t", "")
                .replace("\r", "")
                .replace("\n", "");
    }

    public Game read(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            Game.Builder gameBuilder = Game.builder();
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                line = normalize(line);
                if (line.length() % 2 != 0) {
                    throw new IllegalArgumentException("Line has illegal format: " + line);
                }
                for (int i = 0; i < line.length(); i += 2) {
                    gameBuilder.addCard(Card.of(line.substring(i, i + 2)));
                }
            }
            return gameBuilder.build();
        }
    }
}
