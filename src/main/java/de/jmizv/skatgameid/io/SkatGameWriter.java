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
            write(game, pw);
        }
    }

    public void write(Game game, PrintWriter pw) {
        game.frontCards().forEach(pw::print);
        pw.println();
        game.middleCards().forEach(pw::print);
        pw.println();
        game.rearCards().forEach(pw::print);
        pw.println();
        game.skatCards().forEach(pw::print);
        pw.println();
    }
}
