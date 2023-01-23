package de.jmizv.skatgameid;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameTypeKindTest {

    @Test
    void should_get_value_of() {
        assertThat(GameTypeKind.of(0)).isEqualTo(GameTypeKind.NULL);
        assertThat(GameTypeKind.of(1)).isEqualTo(GameTypeKind.DIAMONDS);
        assertThat(GameTypeKind.of(2)).isEqualTo(GameTypeKind.HEARTS);
        assertThat(GameTypeKind.of(3)).isEqualTo(GameTypeKind.SPADES);
        assertThat(GameTypeKind.of(4)).isEqualTo(GameTypeKind.CLUBS);
        assertThat(GameTypeKind.of(5)).isEqualTo(GameTypeKind.GRAND);
    }

    @Test
    void should_throw_on_illegal_argument() {
        assertThatThrownBy(() -> GameTypeKind.of(-1)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Could not find enum for value: -1");
        assertThatThrownBy(() -> GameTypeKind.of(6)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Could not find enum for value: 6");
    }
}