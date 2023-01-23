package de.jmizv.skatgameid.io;

import de.jmizv.skatgameid.*;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SkatGameRunReader {

    public GameRun read(File file) throws IOException {
        return read(Files.newInputStream(file.toPath()));
    }

    public GameRun read(InputStream inputStream) throws IOException {
        SkatGameReader skatGameReader = new SkatGameReader();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            // Read the game, i.e. the card distribution
            Game game = skatGameReader.read(reader);
            // next are the highest bids from all players.
            String foreHandBid = Util.removeCommentAndNormalize(reader.readLine());
            String midHandBid = Util.removeCommentAndNormalize(reader.readLine());
            String rearHandBid = Util.removeCommentAndNormalize(reader.readLine());
            // the solo player's droped skat cards. Can be the same if they play hand.
            String droppedSkatLine = reader.readLine();
            Card[] droppedSkat = new Card[]{Card.of(droppedSkatLine.substring(0, 2)), Card.of(droppedSkatLine.substring(2, 4))};
            // Now the game type
            String gameTypeLine = Util.removeCommentAndNormalize(reader.readLine());
            GameType gameType = GameType.of(gameTypeLine);
            String cardsPlayedLine;
            List<int[]> cardsPlayed = new ArrayList<>();
            // and finally the game play, i.e. which tricks are played.
            while ((cardsPlayedLine = reader.readLine()) != null) {
                cardsPlayed.add(new int[]{Integer.parseInt(cardsPlayedLine.substring(0, 1)),
                        Integer.parseInt(cardsPlayedLine.substring(1, 2)),
                        Integer.parseInt(cardsPlayedLine.substring(2, 3))});
            }
            return new GameRun(game,
                    new int[]{Integer.parseInt(foreHandBid), Integer.parseInt(midHandBid), Integer.parseInt(rearHandBid)},
                    droppedSkat,
                    gameType,
                    cardsPlayed.toArray(new int[0][]));
        }
    }
}
