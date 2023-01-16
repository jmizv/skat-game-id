package de.jmizv.skatgameid;

import java.util.stream.Stream;

public enum Suit {

    // S_chellen ♦, R_ot ♥, G_rün ♠, E_icheln ♣
    // need to be in that order
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
        return Stream.of(values())
                .filter(suit -> String.valueOf(suit.getDecoded()).equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No suit exists for value: " + name));
    }

    public static Suit of(int ordinal) {
        return Stream.of(values())
                .filter(suit -> suit.ordinal() == ordinal)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No suit exists for ordinal number: " + ordinal));
    }
}
