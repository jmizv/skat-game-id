package de.jmizv.skatgameid;

import java.util.stream.Stream;

public enum Value {

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
        return Stream.of(values()).filter(p -> String.valueOf(p.getDecoded()).equals(name))
                .findAny().orElseThrow(() -> new IllegalArgumentException("No card exists for value: " + name));
    }

    public static Value of(int ordinal) {
        for (Value value : values()) {
            if (value.ordinal() == ordinal) {
                return value;
            }
        }
        throw new IllegalArgumentException("There is no value for ordinal number: " + ordinal);
    }
}
