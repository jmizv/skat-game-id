package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValueTest {

    @Test
    void should_get_correct_value() {
        assertThat(Value.of("7")).isEqualTo(Value.SEVEN);
        assertThat(Value.of("8")).isEqualTo(Value.EIGHT);
        assertThat(Value.of("9")).isEqualTo(Value.NINE);
        assertThat(Value.of("X")).isEqualTo(Value.TEN);
        assertThat(Value.of("U")).isEqualTo(Value.JACK);
        assertThat(Value.of("O")).isEqualTo(Value.QUEEN);
        assertThat(Value.of("K")).isEqualTo(Value.KING);
        assertThat(Value.of("A")).isEqualTo(Value.ACE);
    }

    @Test
    void should_throw_on_illegal_argument() {
        assertThatThrownBy(() -> Value.of("KK")).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No card exists for value: KK");
        assertThatThrownBy(() -> Value.of("")).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No card exists for value: ");
        assertThatThrownBy(() -> Value.of(8)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("There is no value for ordinal number: 8");
    }

}