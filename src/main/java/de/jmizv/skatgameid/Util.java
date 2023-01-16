package de.jmizv.skatgameid;

import java.util.List;

public class Util {

    public static int nextPlayer(List<Card> cards, GameTypeKind gameTypeKind) {
        if (cards == null) {
            throw new NullPointerException("List of cards is null");
        }
        if (cards.size() != 3) {
            throw new IllegalStateException("Cannot determine next player if there are not three cards. Provided " + cards.size() + " cards.");
        }
        if (cards.get(1).beats(gameTypeKind, cards.get(0))) {
            if (cards.get(2).beats(gameTypeKind, cards.get(1))) {
                return 2;
            } else {
                return 1;
            }
        } else {
            if (cards.get(2).beats(gameTypeKind, cards.get(0))) {
                return 2;
            } else {
                return 0;
            }
        }
    }

    public static String normalize(String input) {
        return input.replace(" ", "")
                .replace("\t", "")
                .replace("\r", "")
                .replace("\n", "");
    }

    public static String removeCommentAndNormalize(String line) {
        if (line.contains("--")) {
            // "--" is considered a comment
            line = line.split("--")[0];
        }
        return normalize(line);
    }
}