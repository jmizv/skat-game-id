package de.jmizv.skatgameid;

import java.util.stream.Stream;

public enum Suit {

    // S_chellen ♦, H_erz ♥, G_rün ♠, K_reuz ♣
    DIAMONDS('S'), HEARTS('H'), SPADES('G'), CLUBS('K');
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
}
