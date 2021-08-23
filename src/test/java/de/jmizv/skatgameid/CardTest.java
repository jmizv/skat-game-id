package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class CardTest {

    @Test
    void to_string() {
        Card card = new Card(Suit.CLUBS, Value.ACE);
        assertThat(card.toString()).isEqualTo("EA");
    }

    @Test
    void of_throws() {
        assertThatThrownBy(() -> Card.of("")).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot create a Card of value \"\" as it does not have exactly two characters.");
        assertThatThrownBy(() -> Card.of("S 7")).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot create a Card of value \"S 7\" as it does not have exactly two characters.");
    }

    @Test
    void of() {
        Card cardUpper = Card.of("GU");
        Card cardLower = Card.of("gu");
        assertThat(cardUpper).isEqualTo(cardLower);
    }

    @Test
    void to_number() {
        int counter = 0;
        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                assertThat(new Card(suit, value)).extracting(Card::number).isEqualTo(counter++);
            }
        }
    }

    @Test
    void of_ordinal() {
        Set<Card> cards = new HashSet<>();
        for (int i = 0; i < 32; ++i) {
            Card card = Card.of(i);
            assertThat(cards.add(card)).isTrue();
        }
    }
}
