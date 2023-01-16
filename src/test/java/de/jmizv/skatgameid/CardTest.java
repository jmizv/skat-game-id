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

    @Test
    void can_be_played_upon() {
        assertThat(Card.of("S7").canBePlayedUpon(Card.of("S8"), GameTypeKind.NULL)).isTrue();
        assertThat(Card.of("S7").canBePlayedUpon(Card.of("S8"), GameTypeKind.GRAND)).isTrue();
        assertThat(Card.of("S7").canBePlayedUpon(Card.of("S8"), GameTypeKind.DIAMONDS)).isTrue();
        assertThat(Card.of("S7").canBePlayedUpon(Card.of("S8"), GameTypeKind.HEARTS)).isTrue();
        assertThat(Card.of("S7").canBePlayedUpon(Card.of("S8"), GameTypeKind.SPADES)).isTrue();
        assertThat(Card.of("S7").canBePlayedUpon(Card.of("S8"), GameTypeKind.CLUBS)).isTrue();

        assertThat(Card.of("S7").canBePlayedUpon(Card.of("SU"), GameTypeKind.NULL)).isTrue();
        assertThat(Card.of("S7").canBePlayedUpon(Card.of("SU"), GameTypeKind.GRAND)).isFalse();
        assertThat(Card.of("S7").canBePlayedUpon(Card.of("SU"), GameTypeKind.DIAMONDS)).isTrue();
        assertThat(Card.of("S7").canBePlayedUpon(Card.of("SU"), GameTypeKind.HEARTS)).isFalse();
        assertThat(Card.of("S7").canBePlayedUpon(Card.of("SU"), GameTypeKind.SPADES)).isFalse();
        assertThat(Card.of("S7").canBePlayedUpon(Card.of("SU"), GameTypeKind.CLUBS)).isFalse();

        assertThat(Card.of("SU").canBePlayedUpon(Card.of("S9"), GameTypeKind.NULL)).isTrue();
        assertThat(Card.of("SU").canBePlayedUpon(Card.of("S9"), GameTypeKind.GRAND)).isFalse();
        assertThat(Card.of("SU").canBePlayedUpon(Card.of("S9"), GameTypeKind.DIAMONDS)).isTrue();
        assertThat(Card.of("SU").canBePlayedUpon(Card.of("S9"), GameTypeKind.HEARTS)).isFalse();
        assertThat(Card.of("SU").canBePlayedUpon(Card.of("S9"), GameTypeKind.SPADES)).isFalse();
        assertThat(Card.of("SU").canBePlayedUpon(Card.of("S9"), GameTypeKind.CLUBS)).isFalse();
    }

    @Test
    void is_trump() {
        assertThat(Card.of("GU").isTrump(GameTypeKind.GRAND)).isTrue();
        assertThat(Card.of("GU").isTrump(GameTypeKind.NULL)).isFalse();
        assertThat(Card.of("GU").isTrump(GameTypeKind.CLUBS)).isTrue();
        assertThat(Card.of("GU").isTrump(GameTypeKind.SPADES)).isTrue();
        assertThat(Card.of("GU").isTrump(GameTypeKind.HEARTS)).isTrue();
        assertThat(Card.of("GU").isTrump(GameTypeKind.DIAMONDS)).isTrue();

        assertThat(Card.of("SA").isTrump(GameTypeKind.GRAND)).isFalse();
        assertThat(Card.of("SA").isTrump(GameTypeKind.NULL)).isFalse();
        assertThat(Card.of("SA").isTrump(GameTypeKind.CLUBS)).isFalse();
        assertThat(Card.of("SA").isTrump(GameTypeKind.SPADES)).isFalse();
        assertThat(Card.of("SA").isTrump(GameTypeKind.HEARTS)).isFalse();
        assertThat(Card.of("SA").isTrump(GameTypeKind.DIAMONDS)).isTrue();

        assertThat(Card.of("E7").isTrump(GameTypeKind.GRAND)).isFalse();
        assertThat(Card.of("E7").isTrump(GameTypeKind.NULL)).isFalse();
        assertThat(Card.of("E7").isTrump(GameTypeKind.CLUBS)).isTrue();
        assertThat(Card.of("E7").isTrump(GameTypeKind.SPADES)).isFalse();
        assertThat(Card.of("E7").isTrump(GameTypeKind.HEARTS)).isFalse();
        assertThat(Card.of("E7").isTrump(GameTypeKind.DIAMONDS)).isFalse();

        assertThat(Card.of("GX").isTrump(GameTypeKind.GRAND)).isFalse();
        assertThat(Card.of("GX").isTrump(GameTypeKind.NULL)).isFalse();
        assertThat(Card.of("GX").isTrump(GameTypeKind.CLUBS)).isFalse();
        assertThat(Card.of("GX").isTrump(GameTypeKind.SPADES)).isTrue();
        assertThat(Card.of("GX").isTrump(GameTypeKind.HEARTS)).isFalse();
        assertThat(Card.of("GX").isTrump(GameTypeKind.DIAMONDS)).isFalse();

        assertThat(Card.of("RO").isTrump(GameTypeKind.GRAND)).isFalse();
        assertThat(Card.of("RO").isTrump(GameTypeKind.NULL)).isFalse();
        assertThat(Card.of("RO").isTrump(GameTypeKind.CLUBS)).isFalse();
        assertThat(Card.of("RO").isTrump(GameTypeKind.SPADES)).isFalse();
        assertThat(Card.of("RO").isTrump(GameTypeKind.HEARTS)).isTrue();
        assertThat(Card.of("RO").isTrump(GameTypeKind.DIAMONDS)).isFalse();
    }

    @Test
    void beats_same_card() {
        assertThatThrownBy(() -> Card.of(32).beats(GameTypeKind.GRAND, Card.of(32)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
