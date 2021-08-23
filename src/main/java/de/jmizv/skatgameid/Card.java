package de.jmizv.skatgameid;

import java.util.Locale;
import java.util.Objects;

public class Card implements Comparable<Card> {

    private final Suit _suit;
    private final Value _value;

    public Card(Suit suit, Value value) {
        _suit = Objects.requireNonNull(suit);
        _value = Objects.requireNonNull(value);
    }

    public Suit getSuit() {
        return _suit;
    }

    public Value getValue() {
        return _value;
    }

    @Override
    public String toString() {
        return "" + getSuit() + getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return _suit == card._suit && _value == card._value;
    }

    public int number() {
        return _suit.ordinal() * 8 + _value.ordinal();
    }

    public static Card of(int no) {
        return new Card(Suit.of(no / 8), Value.of(no % 8));
    }

    @Override
    public int hashCode() {
        return Objects.hash(_suit, _value);
    }

    public static Card of(String value) {
        if (value.length() != 2) {
            throw new IllegalArgumentException("Cannot create a Card of value \"" + value + "\" as it does not have exactly two characters.");
        }
        String[] split = value.toUpperCase(Locale.ROOT).split("");
        return new Card(Suit.of(split[0]), Value.of(split[1]));
    }

    @Override
    public int compareTo(Card o) {
        return number() - o.number();
    }
}
