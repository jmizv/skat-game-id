package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CardTest {

    @Test
    void to_string() {
        Card card = new Card(Suit.CLUBS, Value.ACE);
        assertThat(card.toString()).isEqualTo("KA");
    }
}
