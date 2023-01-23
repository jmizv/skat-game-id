package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SuitTest {

    @Test
    void should_throw_on_illegal_argument_for_ordinal() {
        assertThatThrownBy(() -> Suit.of(-1)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No suit exists for ordinal number: -1");
        assertThatThrownBy(() -> Suit.of(4)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No suit exists for ordinal number: 4");
    }

    @ParameterizedTest
    @ValueSource(strings = {"S", "R", "G", "E"})
    void should_get_by_name(String name) {
        assertThatCode(() -> Suit.of(name)).doesNotThrowAnyException();
    }

    @Test
    void should_throw_on_illegal_argument_for_name() {
        assertThatThrownBy(() -> Suit.of("SR")).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No suit exists for value: SR");
        assertThatThrownBy(() -> Suit.of("")).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No suit exists for value: ");
        assertThatThrownBy(() -> Suit.of(" S")).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No suit exists for value:  S");
    }
}