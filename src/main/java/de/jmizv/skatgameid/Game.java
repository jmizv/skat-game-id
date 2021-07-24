package de.jmizv.skatgameid;

import com.google.common.collect.ImmutableList;

import java.util.*;

import static com.google.common.base.Verify.verify;

public class Game {

    private final List<Card> _player1;// 10 cards
    private final List<Card> _player2; // 10 cards
    private final List<Card> _player3; // 10 cards
    private final List<Card> _skat; // 2 cards

    private Game(List<Card> player1, List<Card> player2, List<Card> player3, List<Card> skat) {
        verify(Objects.requireNonNull(player1).size() == 10, "Player 1 needs 10 cards.");
        verify(Objects.requireNonNull(player2).size() == 10, "Player 2 needs 10 cards.");
        verify(Objects.requireNonNull(player3).size() == 10, "Player 3 needs 10 cards.");
        verify(Objects.requireNonNull(skat).size() == 2, "The Skat needs 2 cards.");
        _player1 = ImmutableList.copyOf(player1);
        _player2 = ImmutableList.copyOf(player2);
        _player3 = ImmutableList.copyOf(player3);
        _skat = ImmutableList.copyOf(skat);
    }

    public List<Card> getPlayer1() {
        return _player1;
    }

    public List<Card> getPlayer2() {
        return _player2;
    }

    public List<Card> getPlayer3() {
        return _player3;
    }

    public List<Card> getSkat() {
        return _skat;
    }

    public String computeId() {
        return null;
    }

    public static Game ofId(String id) {
        return null;
    }

    public static Game.Builder builder() {
        return new Game.Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return _player1.equals(game._player1) && _player2.equals(game._player2) && _player3.equals(game._player3) && _skat.equals(game._skat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_player1, _player2, _player3, _skat);
    }

    public static Game random() {
        return random(System.currentTimeMillis());
    }

    public static Game random(long seed) {
        List<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                cards.add(new Card(suit, value));
            }
        }
        Collections.shuffle(cards, new Random(seed));
        return new Game(cards.subList(0, 10),
                cards.subList(10, 20),
                cards.subList(20, 30),
                cards.subList(30, 32));
    }

    public static class Builder {

        private final List<Card> _player1 = new ArrayList<>();// 10 cards
        private final List<Card> _player2 = new ArrayList<>(); // 10 cards
        private final List<Card> _player3 = new ArrayList<>(); // 10 cards
        private final List<Card> _skat = new ArrayList<>(); // 2 cards

        private transient final Set<Card> _all = new HashSet<>() {
            @Override
            public boolean add(Card card) {
                boolean add = super.add(card);
                if (!add) {
                    throw new IllegalArgumentException(String.format("Card %s is already present.", card));
                }
                return true;
            }
        };

        public Builder addCard(Card card, int player) {
            _all.add(card);
            switch (player) {
                case 1 -> addToPlayer(_player1, card);
                case 2 -> addToPlayer(_player2, card);
                case 3 -> addToPlayer(_player3, card);
                default -> throw new IllegalArgumentException(String.format("Player %s does not exist.", player));
            }
            return this;
        }

        private static void addToPlayer(List<Card> cardList, Card card) {
            checkSizeBeforeAdding(cardList, 10);
            cardList.add(card);
        }

        public Builder addCardToSkat(Card card) {
            checkSizeBeforeAdding(_skat, 2);
            _all.add(card);
            _skat.add(card);
            return this;
        }

        private static void checkSizeBeforeAdding(List<Card> cardList, int maximumAmount) {
            if (cardList.size() == maximumAmount) {
                throw new IllegalArgumentException("Cannot add any more card. Have already " + maximumAmount);
            }
        }

        public Game build() {
            if (_all.size() != 32) {
                throw new IllegalStateException("Game cannot be build. There are only " + _all + " card(s) set.");
            }
            return new Game(_player1, _player2, _player3, _skat);
        }
    }
}
