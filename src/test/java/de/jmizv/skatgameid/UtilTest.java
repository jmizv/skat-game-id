package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UtilTest {

    private static List<Card> of(Card c1, Card c2, Card c3) {
        return Arrays.asList(c1, c2, c3);
    }

    @Test
    void next_player() {
        assertThat(Util.nextPlayer(of(Card.of("EU"), Card.of("GU"), Card.of("RU")), GameTypeKind.GRAND)).isEqualTo(0);
        assertThat(Util.nextPlayer(of(Card.of("SU"), Card.of("GU"), Card.of("RU")), GameTypeKind.GRAND)).isEqualTo(1);
        assertThat(Util.nextPlayer(of(Card.of("SU"), Card.of("RU"), Card.of("GU")), GameTypeKind.GRAND)).isEqualTo(2);

        assertThat(Util.nextPlayer(of(Card.of("EU"), Card.of("GU"), Card.of("RU")), GameTypeKind.SPADES)).isEqualTo(0);
        assertThat(Util.nextPlayer(of(Card.of("SU"), Card.of("GU"), Card.of("RU")), GameTypeKind.HEARTS)).isEqualTo(1);
        assertThat(Util.nextPlayer(of(Card.of("SU"), Card.of("RU"), Card.of("GU")), GameTypeKind.DIAMONDS)).isEqualTo(2);

        assertThatThrownBy(() -> Util.nextPlayer(of(Card.of("EU"), Card.of("GU"), Card.of("RU")), GameTypeKind.NULL)).isInstanceOf(UnsupportedOperationException.class);
        //assertThat(Util.nextPlayer(of(Card.of("EU"), Card.of("GU"), Card.of("RU")), GameType.GameTypeKind.NULL)).isEqualTo(0);
        //assertThat(Util.nextPlayer(of(Card.of("SU"), Card.of("GU"), Card.of("RU")), GameType.GameTypeKind.NULL)).isEqualTo(0);
        //assertThat(Util.nextPlayer(of(Card.of("SU"), Card.of("RU"), Card.of("GU")), GameType.GameTypeKind.NULL)).isEqualTo(0);

        assertThat(Util.nextPlayer(of(Card.of("SU"), Card.of("S7"), Card.of("R8")), GameTypeKind.GRAND)).isEqualTo(0);
        assertThat(Util.nextPlayer(of(Card.of("R9"), Card.of("RU"), Card.of("GX")), GameTypeKind.GRAND)).isEqualTo(1);
        assertThat(Util.nextPlayer(of(Card.of("EO"), Card.of("SK"), Card.of("GU")), GameTypeKind.GRAND)).isEqualTo(2);

        assertThat(Util.nextPlayer(of(Card.of("RA"), Card.of("SU"), Card.of("GU")), GameTypeKind.GRAND)).isEqualTo(2);
        assertThat(Util.nextPlayer(of(Card.of("RU"), Card.of("S7"), Card.of("SU")), GameTypeKind.GRAND)).isEqualTo(0);
        assertThat(Util.nextPlayer(of(Card.of("RU"), Card.of("GU"), Card.of("RX")), GameTypeKind.GRAND)).isEqualTo(1);

        assertThat(Util.nextPlayer(of(Card.of("S9"), Card.of("SA"), Card.of("SX")), GameTypeKind.DIAMONDS)).isEqualTo(1);
        assertThat(Util.nextPlayer(of(Card.of("EX"), Card.of("E7"), Card.of("E8")), GameTypeKind.DIAMONDS)).isEqualTo(0);
        assertThat(Util.nextPlayer(of(Card.of("RO"), Card.of("RK"), Card.of("RA")), GameTypeKind.DIAMONDS)).isEqualTo(2);

        assertThat(Util.nextPlayer(of(Card.of("SX"), Card.of("S9"), Card.of("R7")), GameTypeKind.DIAMONDS)).isEqualTo(0);
        assertThat(Util.nextPlayer(of(Card.of("SX"), Card.of("S9"), Card.of("R7")), GameTypeKind.SPADES)).isEqualTo(0);
        assertThat(Util.nextPlayer(of(Card.of("SX"), Card.of("S9"), Card.of("R7")), GameTypeKind.HEARTS)).isEqualTo(2);

        assertThat(Util.nextPlayer(of(Card.of("R9"), Card.of("RX"), Card.of("G7")), GameTypeKind.DIAMONDS)).isEqualTo(1);
        assertThat(Util.nextPlayer(of(Card.of("R9"), Card.of("RX"), Card.of("G7")), GameTypeKind.SPADES)).isEqualTo(2);
        assertThat(Util.nextPlayer(of(Card.of("R9"), Card.of("RX"), Card.of("G7")), GameTypeKind.HEARTS)).isEqualTo(1);

        assertThat(Util.nextPlayer(of(Card.of("R9"), Card.of("RX"), Card.of("R7")), GameTypeKind.GRAND)).isEqualTo(1);
        assertThat(Util.nextPlayer(of(Card.of("RK"), Card.of("RX"), Card.of("R7")), GameTypeKind.GRAND)).isEqualTo(0);
        assertThat(Util.nextPlayer(of(Card.of("R9"), Card.of("RX"), Card.of("RK")), GameTypeKind.GRAND)).isEqualTo(2);

        assertThat(Util.nextPlayer(of(Card.of("GO"), Card.of("GU"), Card.of("E7")), GameTypeKind.SPADES)).isEqualTo(1);
    }
}