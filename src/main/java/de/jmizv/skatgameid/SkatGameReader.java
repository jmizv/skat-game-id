package de.jmizv.skatgameid;

import java.io.*;

public class SkatGameReader {

    public Game read(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        Game.Builder gameBuilder = Game.builder();
        int num = 0;
        while ((line = reader.readLine()) != null) {
            if (num == 0) {

            }

            ++num;
        }
        return gameBuilder.build();
    }

    private void add(String line, ) {

    }
}
