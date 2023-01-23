package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UtilTest {

    private static List<Card> of(Card c1, Card c2, Card c3) {
        return Arrays.asList(c1, c2, c3);
    }

    @Test
    void should_compute_next_player() {
        assertThat(Util.nextPlayer(of(Card.of("EU"), Card.of("GU"), Card.of("RU")), GameTypeKind.GRAND)).isEqualTo(0);
        assertThat(Util.nextPlayer(of(Card.of("SU"), Card.of("GU"), Card.of("RU")), GameTypeKind.GRAND)).isEqualTo(1);
        assertThat(Util.nextPlayer(of(Card.of("SU"), Card.of("RU"), Card.of("GU")), GameTypeKind.GRAND)).isEqualTo(2);

        assertThat(Util.nextPlayer(of(Card.of("EU"), Card.of("GU"), Card.of("RU")), GameTypeKind.SPADES)).isEqualTo(0);
        assertThat(Util.nextPlayer(of(Card.of("SU"), Card.of("GU"), Card.of("RU")), GameTypeKind.HEARTS)).isEqualTo(1);
        assertThat(Util.nextPlayer(of(Card.of("SU"), Card.of("RU"), Card.of("GU")), GameTypeKind.DIAMONDS)).isEqualTo(2);

        assertThat(Util.nextPlayer(of(Card.of("EU"), Card.of("GU"), Card.of("RU")), GameTypeKind.NULL)).isEqualTo(0);
        assertThat(Util.nextPlayer(of(Card.of("EU"), Card.of("GU"), Card.of("RU")), GameTypeKind.NULL)).isEqualTo(0);
        assertThat(Util.nextPlayer(of(Card.of("SU"), Card.of("GU"), Card.of("RU")), GameTypeKind.NULL)).isEqualTo(0);
        assertThat(Util.nextPlayer(of(Card.of("SU"), Card.of("RU"), Card.of("GU")), GameTypeKind.NULL)).isEqualTo(0);

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

    @Test
    void should_create_integer_from_bit_set() {
        BitSet bitSet = new BitSet(10);
        // 1010101010  = 2 + 8 + 32 + 128 + 512
        for (int i = 0; i < 10; i += 2) {
            bitSet.set(i);
        }
        assertThat(Util.integerFromBitSet(bitSet, 0, 0)).isEqualTo(0);
        assertThat(Util.integerFromBitSet(bitSet, 0, 1)).isEqualTo(1);
        assertThat(Util.integerFromBitSet(bitSet, 0, 2)).isEqualTo(2);
        assertThat(Util.integerFromBitSet(bitSet, 0, 3)).isEqualTo(5);
        assertThat(Util.integerFromBitSet(bitSet, 0, 4)).isEqualTo(10);
        assertThat(Util.integerFromBitSet(bitSet, 0, 10)).isEqualTo(682);

        assertThat(Util.integerFromBitSet(bitSet, 1, 0)).isEqualTo(0);
        assertThat(Util.integerFromBitSet(bitSet, 1, 1)).isEqualTo(0);
        assertThat(Util.integerFromBitSet(bitSet, 1, 2)).isEqualTo(1);
        assertThat(Util.integerFromBitSet(bitSet, 1, 3)).isEqualTo(2);
        assertThat(Util.integerFromBitSet(bitSet, 1, 4)).isEqualTo(5);
        assertThat(Util.integerFromBitSet(bitSet, 1, 9)).isEqualTo(170);
    }

    @Test
    void should_set_bit_set() {
        BitSet set = new BitSet(100);
        Util.setBitSet(set, 4, 95, 3);
        assertThat(set.get(95)).isTrue();
        assertThat(set.get(96)).isFalse();
        assertThat(set.get(97)).isFalse();

        Util.setBitSet(set, 5, 35, 3);
        assertThat(set.get(35)).isTrue();
        assertThat(set.get(36)).isFalse();
        assertThat(set.get(37)).isTrue();
        // edge cases?
    }

    @Test
    void should_set_and_get_integer() {
        BitSet set = new BitSet(100);
        Util.setBitSet(set, 4, 55, 3);
        assertThat(Util.integerFromBitSet(set, 55, 3)).isEqualTo(4);

        Util.setBitSet(set, 3, 25, 5);
        assertThat(Util.integerFromBitSet(set, 25, 5)).isEqualTo(3);
    }
}