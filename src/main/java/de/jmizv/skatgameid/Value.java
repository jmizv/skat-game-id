package de.jmizv.skatgameid;

import java.util.stream.Stream;

public enum Value {

    // probably needs variance for like Queen could be Q, O, or D.
    SEVEN('7'), EIGHT('8'), NINE('9'), TEN('X'), JACK('U'), QUEEN('O'), KING('K'), ACE('A');

    private final char _decoded;

    Value(char decoded) {
        _decoded = decoded;
    }

    public char getDecoded() {
        return _decoded;
    }

    @Override
    public String toString() {
        return String.valueOf(_decoded);
    }

    public static Value of(String name) {
        return Stream.of(values())
                .filter(p -> String.valueOf(p.getDecoded()).equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No card exists for value: " + name));
    }

    public static Value of(int ordinal) {
        return Stream.of(values())
                .filter(v -> v.ordinal() == ordinal)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("There is no value for ordinal number: " + ordinal));
    }
}
