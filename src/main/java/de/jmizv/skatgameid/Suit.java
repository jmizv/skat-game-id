package de.jmizv.skatgameid;

import java.util.stream.Stream;

public enum Suit {

    // S_chellen ♦, R_ot ♥, G_rün ♠, E_icheln ♣
    DIAMONDS('S'), HEARTS('R'), SPADES('G'), CLUBS('E');
    private final char _decoded;

    Suit(char decoded) {
        _decoded = decoded;
    }

    public char getDecoded() {
        return _decoded;
    }

    @Override
    public String toString() {
        return String.valueOf(_decoded);
    }

    public static Suit of(String name) {
        return Stream.of(values()).filter(p -> String.valueOf(p.getDecoded()).equals(name))
                .findAny().orElseThrow(() -> new IllegalArgumentException("No card exists for value: " + name));
    }

    public static Suit of(int ordinal) {
        for (Suit suit : values()) {
            if (suit.ordinal() == ordinal) {
                return suit;
            }
        }
        throw new IllegalArgumentException("There is no suit for ordinal number: " + ordinal);
    }
}
