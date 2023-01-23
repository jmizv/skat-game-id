package de.jmizv.skatgameid.io;

import de.jmizv.skatgameid.GameRun;
import de.jmizv.skatgameid.Player;

import java.io.*;
import java.nio.file.Files;

public class SkatGameRunWriter {

    public void write(GameRun gameRun, File file) throws IOException {
        write(gameRun, Files.newOutputStream(file.toPath()));
    }

    public void write(GameRun gameRun, OutputStream outputStream) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)))) {
            new SkatGameWriter().write(gameRun.game(), pw);
            pw.print(gameRun.skatCard(0).toString());
            pw.println(gameRun.skatCard(1).toString());
            pw.println(gameRun.bid(Player.FOREHAND)); // leading 0?
            pw.println(gameRun.bid(Player.MIDHAND)); // leading 0?
            pw.println(gameRun.bid(Player.REARHAND)); // leading 0?
            pw.println(gameRun.gameType().toString());
            for (int i = 0; i < 10; ++i) {
                for (int trick : gameRun.trickAsIndexes(i)) {
                    pw.print(trick);
                }
                pw.println();
            }
        }
    }
}
