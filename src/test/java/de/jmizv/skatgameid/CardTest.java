package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

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
    void cannot_be_played_upon_with_same_two_cards() {
        assertThatThrownBy(() -> Card.of(14).canBePlayedUpon(Card.of(14), GameTypeKind.NULL))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot check for played upon with the same two cards.");
    }

    @ParameterizedTest
    @MethodSource("canBePlayedUponArguments")
    void can_be_played_upon(Card card, Card toBePlayed, GameTypeKind gameTypeKind, boolean expectedResult) {
        assertThat(card.canBePlayedUpon(toBePlayed, gameTypeKind)).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> canBePlayedUponArguments() {
        return Stream.of(
                Arguments.of(Card.of("S7"), Card.of("S8"), GameTypeKind.NULL, true),
                Arguments.of(Card.of("S7"), Card.of("S8"), GameTypeKind.GRAND, true),
                Arguments.of(Card.of("S7"), Card.of("S8"), GameTypeKind.DIAMONDS, true),
                Arguments.of(Card.of("S7"), Card.of("S8"), GameTypeKind.HEARTS, true),
                Arguments.of(Card.of("S7"), Card.of("S8"), GameTypeKind.SPADES, true),
                Arguments.of(Card.of("S7"), Card.of("S8"), GameTypeKind.CLUBS, true),

                Arguments.of(Card.of("S7"), Card.of("SU"), GameTypeKind.NULL, true),
                Arguments.of(Card.of("S7"), Card.of("SU"), GameTypeKind.GRAND, false),
                Arguments.of(Card.of("S7"), Card.of("SU"), GameTypeKind.DIAMONDS, true),
                Arguments.of(Card.of("S7"), Card.of("SU"), GameTypeKind.HEARTS, false),
                Arguments.of(Card.of("S7"), Card.of("SU"), GameTypeKind.SPADES, false),
                Arguments.of(Card.of("S7"), Card.of("SU"), GameTypeKind.CLUBS, false),

                Arguments.of(Card.of("SU"), Card.of("S9"), GameTypeKind.NULL, true),
                Arguments.of(Card.of("SU"), Card.of("S9"), GameTypeKind.GRAND, false),
                Arguments.of(Card.of("SU"), Card.of("S9"), GameTypeKind.DIAMONDS, true),
                Arguments.of(Card.of("SU"), Card.of("S9"), GameTypeKind.HEARTS, false),
                Arguments.of(Card.of("SU"), Card.of("S9"), GameTypeKind.SPADES, false),
                Arguments.of(Card.of("SU"), Card.of("S9"), GameTypeKind.CLUBS, false),

                Arguments.of(Card.of("SU"), Card.of("EU"), GameTypeKind.GRAND, true),
                Arguments.of(Card.of("R9"), Card.of("E8"), GameTypeKind.GRAND, false),
                Arguments.of(Card.of("R9"), Card.of("E8"), GameTypeKind.DIAMONDS, false),
                Arguments.of(Card.of("R9"), Card.of("E8"), GameTypeKind.NULL, false)
        );
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
    void should_throw_on_beat_same_card() {
        assertThatThrownBy(() -> Card.of(31).beats(GameTypeKind.GRAND, Card.of(31)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Can not check \"beats\" to the same card.");
    }

    @ParameterizedTest
    @MethodSource("cardsToBeatForGrand")
    void should_beat_in_grand(Card foreHand, Card midHand, Card rearHand, int cardThatWins) {
        assertThat(Util.nextPlayer(GameTypeKind.GRAND, foreHand, midHand, rearHand)).isEqualTo(cardThatWins);
    }

    static Stream<Arguments> cardsToBeatForGrand() {
        return Stream.of(
                Arguments.arguments(Card.of("S7"), Card.of("SA"), Card.of("SU"), 2),
                Arguments.arguments(Card.of("S7"), Card.of("SA"), Card.of("EU"), 2),
                Arguments.arguments(Card.of("S7"), Card.of("RU"), Card.of("EU"), 2),
                Arguments.arguments(Card.of("SX"), Card.of("S7"), Card.of("RU"), 2),

                Arguments.arguments(Card.of("S7"), Card.of("SA"), Card.of("SX"), 1),
                Arguments.arguments(Card.of("S7"), Card.of("SA"), Card.of("SK"), 1),
                Arguments.arguments(Card.of("S7"), Card.of("SA"), Card.of("RK"), 1),
                Arguments.arguments(Card.of("SX"), Card.of("SA"), Card.of("RK"), 1),

                Arguments.arguments(Card.of("S9"), Card.of("S8"), Card.of("S7"), 0),
                Arguments.arguments(Card.of("S9"), Card.of("E8"), Card.of("E7"), 0),
                Arguments.arguments(Card.of("S9"), Card.of("E8"), Card.of("S7"), 0),
                Arguments.arguments(Card.of("S9"), Card.of("S8"), Card.of("E7"), 0)
        );
    }

    @ParameterizedTest
    @MethodSource("cardsToBeatForNull")
    void should_beat_in_null(Card foreHand, Card midHand, Card rearHand, int cardThatWins) {
        assertThat(Util.nextPlayer(GameTypeKind.NULL, foreHand, midHand, rearHand)).isEqualTo(cardThatWins);
    }

    static Stream<Arguments> cardsToBeatForNull() {
        return Stream.of(
                Arguments.arguments(Card.of("S7"), Card.of("S8"), Card.of("S9"), 2),
                Arguments.arguments(Card.of("S7"), Card.of("S9"), Card.of("S8"), 1),
                Arguments.arguments(Card.of("S9"), Card.of("S7"), Card.of("S8"), 0),

                Arguments.arguments(Card.of("S7"), Card.of("E8"), Card.of("S9"), 2),
                Arguments.arguments(Card.of("S7"), Card.of("S9"), Card.of("E8"), 1),
                Arguments.arguments(Card.of("S9"), Card.of("S7"), Card.of("E8"), 0),

                Arguments.arguments(Card.of("SX"), Card.of("SU"), Card.of("SA"), 2),
                Arguments.arguments(Card.of("SX"), Card.of("SA"), Card.of("SU"), 1),
                Arguments.arguments(Card.of("SA"), Card.of("SX"), Card.of("SU"), 0),

                Arguments.arguments(Card.of("S9"), Card.of("EO"), Card.of("RK"), 0),
                Arguments.arguments(Card.of("EO"), Card.of("S9"), Card.of("RK"), 0),
                Arguments.arguments(Card.of("EO"), Card.of("RK"), Card.of("S9"), 0)
        );
    }
}
