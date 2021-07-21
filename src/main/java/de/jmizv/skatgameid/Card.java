package de.jmizv.skatgameid;

import java.util.Locale;
import java.util.Objects;

public class Card {
    private final Suit suit;
    private final Value value;

    public Card(Suit suit, Value value) {
        this.suit = Objects.requireNonNull(suit);
        this.value = Objects.requireNonNull(value);
    }

    public Suit getSuit() {
        return suit;
    }

    public Value getValue() {
        return value;
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
        return suit == card.suit && value == card.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, value);
    }

    public static Card of(String value) {
        String[] split = value.toUpperCase(Locale.ROOT).split("");
        return new Card(Suit.of(split[0]), Value.of(split[1]));
    }
}
