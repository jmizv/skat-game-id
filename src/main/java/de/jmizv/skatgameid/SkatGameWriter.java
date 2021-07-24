package de.jmizv.skatgameid;

import java.io.*;

public class SkatGameWriter {

    public void write(Game game, File file) throws IOException {
        write(game, new FileOutputStream(file));
    }

    public void write(Game game, OutputStream outputStream) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)))) {
            game.getPlayer1().forEach(pw::print);
            pw.println();
            game.getPlayer2().forEach(pw::print);
            pw.println();
            game.getPlayer3().forEach(pw::print);
            pw.println();
            game.getSkat().forEach(pw::print);
            pw.println();
        }
    }
}
