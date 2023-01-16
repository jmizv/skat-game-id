package de.jmizv.skatgameid.io;

import de.jmizv.skatgameid.Game;

import java.io.*;
import java.nio.file.Files;

public class SkatGameWriter {

    public void write(Game game, File file) throws IOException {
        write(game, Files.newOutputStream(file.toPath()));
    }

    public void write(Game game, OutputStream outputStream) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)))) {
            game.getPlayerFront().forEach(pw::print);
            pw.println();
            game.getPlayerMiddle().forEach(pw::print);
            pw.println();
            game.getPlayerRear().forEach(pw::print);
            pw.println();
            game.getSkat().forEach(pw::print);
            pw.println();
        }
    }
}
