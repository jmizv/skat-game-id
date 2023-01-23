package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
    void cards_are_sorted() {
        Game game = Game.random(0);
        assertThat(isSorted(game.frontCards())).isFalse();
        assertThat(isSorted(game.middleCards())).isFalse();
        assertThat(isSorted(game.rearCards())).isFalse();

        Game normalized = game.normalized();
        assertThat(isSorted(normalized.frontCards())).isTrue();
        assertThat(isSorted(normalized.middleCards())).isTrue();
        assertThat(isSorted(normalized.rearCards())).isTrue();
        assertThat(isSorted(normalized.skatCards())).isTrue();
    }

    private boolean isSorted(List<Card> cards) {
        for (int i = 1; i < cards.size(); ++i) {
            Card card1 = cards.get(i - 1);
            Card card2 = cards.get(i);
            if (card1.compareTo(card2) > 0) {
                return false;
            }
        }
        return true;
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
                .addCard(Card.of("R7"), 1)
                .addCard(Card.of("R8"), 1);
        assertThatThrownBy(() -> gameBuilder.addCard(Card.of("R8"), 1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void not_more_than_2_cards_for_trick() {
        Game.Builder gameBuilder = Game.builder();
        gameBuilder.addCardToSkat(Card.of("S7"))
                .addCardToSkat(Card.of("S8"));
        assertThatThrownBy(() -> gameBuilder.addCardToSkat(Card.of("R8"))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void cannot_build_on_less_than_32_cards() {
        Game.Builder gameBuilder = Game.builder();
        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                assertThatThrownBy(gameBuilder::build).isInstanceOf(IllegalStateException.class);
                gameBuilder.addCard(new Card(suit, value));
            }
        }
        assertThatCode(gameBuilder::build).doesNotThrowAnyException();
    }

    @Test
    void random_game() {
        Game random1 = Game.random(0);
        Game random2 = Game.random(0);
        Game random3 = Game.random();
        assertThat(random1).isEqualTo(random2);
        assertThat(random1).isNotEqualTo(random3);
    }

    @Test
    void compute_id() {
        Game game = Game.random(0);
        assertThat(game.computeId()).isNotNull();
    }

    @Test
    void from_id() {
        Game game = Game.ofId("MYKAmZXGhaQ");
        assertThat(game).isNotNull();
        assertThat(game).isEqualTo(Game.random(0).normalized());
    }

    @Test
    void test_exceptions() {
        assertThatThrownBy(() -> Game.ofId("")).isInstanceOf(IllegalArgumentException.class).hasMessage("Game id \"\" is too short.");
        assertThatThrownBy(() -> Game.ofId("0")).isInstanceOf(IllegalArgumentException.class).hasMessage("Game id \"0\" is too short.");
        assertThatThrownBy(() -> Game.ofId("g")).isInstanceOf(IllegalArgumentException.class).hasMessage("Game id \"g\" is too short.");
        assertThatThrownBy(() -> Game.ofId("0000")).isInstanceOf(IllegalArgumentException.class).hasMessage("Cannot add any more cards. Have already 2 cards for Skat.");
        assertThatThrownBy(() -> Game.ofId("mykAmZXGhaQ")).isInstanceOf(IllegalArgumentException.class).hasMessage("Cannot add any more cards. Have already 10 cards for Middle-Player.");
        assertThatThrownBy(() -> Game.ofId("mykAmZXGhaQmykAmZXGhaQ")).isInstanceOf(IllegalArgumentException.class).hasMessage("BitSet is not compatible for decoding as it results in 64 cards.");
    }
}