package de.jmizv.skatgameid;

import java.io.*;
import java.util.function.Consumer;

public class SkatGameReader {

    public Game read(File file) throws IOException {
        return read(new FileInputStream(file));
    }

    private static void add(String line, Consumer<Card> consumer) {
        for (int i = 0; i < line.length(); i += 2) {
            String substring = line.substring(i, i + 2);
            consumer.accept(Card.of(substring));
        }
    }

    public Game read(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            Game.Builder gameBuilder = Game.builder();
            add(reader.readLine(), card -> gameBuilder.addCard(card, 1));
            add(reader.readLine(), card -> gameBuilder.addCard(card, 2));
            add(reader.readLine(), card -> gameBuilder.addCard(card, 3));
            add(reader.readLine(), gameBuilder::addCardToSkat);
            return gameBuilder.build();
        }
    }
}
