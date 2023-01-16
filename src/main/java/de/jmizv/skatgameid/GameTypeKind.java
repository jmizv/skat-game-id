package de.jmizv.skatgameid;

import java.util.stream.Stream;

public enum GameTypeKind {
    NULL, // 0
    DIAMONDS, // 1
    HEARTS, // 2
    SPADES, // 3
    CLUBS, // 4
    GRAND; // 5

    public static GameTypeKind of(String ordinalAsString) {
        return of(Integer.parseInt(ordinalAsString));
    }

    public static GameTypeKind of(int ordinal) {
        return Stream.of(values())
                .filter(kind -> kind.ordinal() == ordinal)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Could not find enum for value: " + ordinal));
    }
}
