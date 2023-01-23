package de.jmizv.skatgameid;

import java.util.BitSet;
import java.util.List;

public class Util {

    public static int nextPlayer(List<Card> cards, GameTypeKind gameTypeKind) {
        if (cards == null) {
            throw new NullPointerException("List of cards is null");
        }
        if (cards.size() != 3) {
            throw new IllegalStateException("Cannot determine next player if there are not three cards. Provided " + cards.size() + " cards.");
        }
        return nextPlayer(gameTypeKind, cards.get(0), cards.get(1), cards.get(2));
    }
    
    public static int nextPlayer(GameTypeKind gameTypeKind, Card foreHand, Card midHand, Card rearHand) {
        if (midHand.beats(gameTypeKind, foreHand)) {
            if (rearHand.beats(gameTypeKind, midHand)) {
                return 2;
            } else {
                return 1;
            }
        } else {
            if (rearHand.beats(gameTypeKind, foreHand)) {
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

    public static int integerFromBitSet(BitSet input, int startIndex, int length) {
        if (length == 0) {
            return 0;
        }
        int result = 0;
        for (int i = 0; i < length; ++i) {
            if (input.get(startIndex + i)) {
                result = result | (0x1 << length - i - 1);
            }
        }
        return result;
    }

    public static void setBitSet(BitSet bitSet, int number, int startingIndex, int maxLength) {
        for (int idx = 0; idx < maxLength; ++idx) {
            if ((number & (0x01 << idx)) != 0) {
                bitSet.set(startingIndex + maxLength - idx - 1);
            }
        }
    }
}