package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameTest {

    @Test
    void player_does_not_exist() {
        Game.Builder gameBuilder = Game.builder();
        assertThatThrownBy(() -> gameBuilder.addCard(Card.of("S7"), 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> gameBuilder.addCard(Card.of("S7"), 4)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void no_duplicate_cards() {
        Game.Builder gameBuilder = Game.builder();
        gameBuilder.addCard(Card.of("S7"), 1);
        assertThatThrownBy(() -> gameBuilder.addCard(Card.of("S7"), 1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> gameBuilder.addCard(Card.of("S7"), 2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void not_more_than_10_cards_for_players() {
        Game.Builder gameBuilder = Game.builder();
        gameBuilder.addCard(Card.of("S7"), 1)
                .addCard(Card.of("S8"), 1)
                .addCard(Card.of("S9"), 1)
                .addCard(Card.of("SX"), 1)
                .addCard(Card.of("SU"), 1)
                .addCard(Card.of("SO"), 1)
                .addCard(Card.of("SK"), 1)
                .addCard(Card.of("SA"), 1)
                .addCard(Card.of("H7"), 1)
                .addCard(Card.of("H8"), 1);
        assertThatThrownBy(() -> gameBuilder.addCard(Card.of("H8"), 1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void not_more_than_2_cards_for_trick() {
        Game.Builder gameBuilder = Game.builder();
        gameBuilder.addCardToSkat(Card.of("S7"))
                .addCardToSkat(Card.of("S8"));
        assertThatThrownBy(() -> gameBuilder.addCardToSkat(Card.of("H8"))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void cannot_build_on_less_than_32_cards() {
        Game.Builder gameBuilder = Game.builder();
        int count = 0;
        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                assertThatThrownBy(gameBuilder::build).isInstanceOf(IllegalStateException.class);
                if (count < 30) {
                    gameBuilder.addCard(new Card(suit, value), 1 + count / 10);
                } else {
                    gameBuilder.addCardToSkat(new Card(suit, value));
                }
                count++;
            }
        }
        assertThatCode(gameBuilder::build).doesNotThrowAnyException();
    }
}