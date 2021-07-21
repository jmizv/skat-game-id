package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameTest {

    @Test
    void player_does_not_exist() {
        Game game = new Game();
        assertThatThrownBy(() -> game.addCard(Card.of("S7"), 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> game.addCard(Card.of("S7"), 4)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void no_duplicate_cards() {
        Game game = new Game();
        game.addCard(Card.of("S7"), 1);
        assertThatThrownBy(() -> game.addCard(Card.of("S7"), 1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> game.addCard(Card.of("S7"), 2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void not_more_than_10_cards_for_players() {
        Game game = new Game();
        game.addCard(Card.of("S7"), 1);
        game.addCard(Card.of("S8"), 1);
        game.addCard(Card.of("S9"), 1);
        game.addCard(Card.of("SX"), 1);
        game.addCard(Card.of("SU"), 1);
        game.addCard(Card.of("SO"), 1);
        game.addCard(Card.of("SK"), 1);
        game.addCard(Card.of("SA"), 1);
        game.addCard(Card.of("H7"), 1);
        game.addCard(Card.of("H8"), 1);
        assertThatThrownBy(() -> game.addCard(Card.of("H8"), 1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void not_more_than_2_cards_for_trick() {
        Game game = new Game();
        game.addCardToTrick(Card.of("S7"));
        game.addCardToTrick(Card.of("S8"));
        assertThatThrownBy(() -> game.addCardToTrick(Card.of("H8"))).isInstanceOf(IllegalArgumentException.class);
    }
}