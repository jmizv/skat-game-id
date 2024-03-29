package de.jmizv.skatgameid;

import com.google.common.collect.ImmutableList;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Verify.verify;

public class Game {

    public static final String SKAT = "Skat";
    public static final String PLAYER_FRONT = "Front";
    public static final String PLAYER_MIDDLE = "Middle";
    public static final String PLAYER_REAR = "Rear";
    public static final String PLAYER = "Player";

    private final List<Card> _player1Front;// 10 cards
    private final List<Card> _player2Middle; // 10 cards
    private final List<Card> _player3Rear; // 10 cards
    private final List<Card> _skat; // 2 cards

    /**
     * Creates a new game instance with the given cards. No sorting is done.
     *
     * @param player1Front  the ten cards for the front player
     * @param player2Middle the ten cards for the middle player
     * @param player3Rear   the ten cards for the rear player
     * @param skat          the two cards for the skat
     */
    private Game(List<Card> player1Front, List<Card> player2Middle, List<Card> player3Rear, List<Card> skat) {
        verify(Objects.requireNonNull(player1Front).size() == 10, "Player 1 needs 10 cards.");
        verify(Objects.requireNonNull(player2Middle).size() == 10, "Player 2 needs 10 cards.");
        verify(Objects.requireNonNull(player3Rear).size() == 10, "Player 3 needs 10 cards.");
        verify(Objects.requireNonNull(skat).size() == 2, "The Skat needs 2 cards.");
        _player1Front = player1Front.stream().collect(ImmutableList.toImmutableList());
        _player2Middle = player2Middle.stream().collect(ImmutableList.toImmutableList());
        _player3Rear = player3Rear.stream().collect(ImmutableList.toImmutableList());
        _skat = skat.stream().collect(ImmutableList.toImmutableList());
    }

    /**
     * @return the same card distribution for this game but with all cards sorted by their values for every player and the skat.
     */
    public Game normalized() {
        return new Game(_player1Front.stream().sorted().collect(Collectors.toList()),
                _player2Middle.stream().sorted().collect(Collectors.toList()),
                _player3Rear.stream().sorted().collect(Collectors.toList()),
                _skat.stream().sorted().collect(Collectors.toList()));
    }

    public List<Card> frontCards() {
        return _player1Front;
    }

    public List<Card> middleCards() {
        return _player2Middle;
    }

    public List<Card> rearCards() {
        return _player3Rear;
    }

    public List<Card> skatCards() {
        return _skat;
    }

    /**
     * Generates the game id in a bitset object.
     * <p>
     * As we have 32 cards and 3 player and the skat, we set for every card to which of the four stacks it belongs. 0x0 is
     * forehand, 0x1 is middle hand, 0x10 is rear hand and finally 0x11 is the skat.
     *
     * @return the game id as a bitset
     */
    BitSet computeIdAsBitSet() {
        BitSet bitSet = new BitSet(32 * 2);
        for (Card card : middleCards()) {
            bitSet.set(card.number() * 2);
        }
        for (Card card : rearCards()) {
            bitSet.set(card.number() * 2 + 1);
        }
        for (Card card : skatCards()) {
            bitSet.set(card.number() * 2);
            bitSet.set(card.number() * 2 + 1);
        }
        return bitSet;
    }

    /**
     * Generates the game id as a base64 encoded string
     *
     * @return the game id as a string
     */
    public String computeId() {
        return Base64.getEncoder().encodeToString(computeIdAsBitSet().toByteArray());
    }

    public BigInteger computeIdAsBigInteger() {
        return new BigInteger(computeIdAsBitSet().toByteArray());
    }

    /**
     * Builds a Game object from the given id. Note that the object will not be normalized,
     * i.e. the order of the cards per player stays unchanged.
     *
     * @param id the game identification
     * @return a Game object
     */

    public static Game ofId(String id) {
        if (id.length() < 2) {
            throw new IllegalArgumentException("Game id \"" + id + "\" is too short.");
        }
        return ofBitSet(BitSet.valueOf(Base64.getDecoder().decode(id)));
    }

     public static Game ofBitSet(BitSet bitSet) {
        if (bitSet.size() != 64) {
            throw new IllegalArgumentException("BitSet is not compatible for decoding as it results in " + bitSet.size() / 2 + " cards.");
        }
        Game.Builder builder = Game.builder();
        for (int i = 0; i < 64; i += 2) {
            if (!bitSet.get(i) && !bitSet.get(i + 1)) {
                builder.addCard(Card.of(i / 2), 1);
            } else if (bitSet.get(i) && !bitSet.get(i + 1)) {
                builder.addCard(Card.of(i / 2), 2);
            } else if (!bitSet.get(i) && bitSet.get(i + 1)) {
                builder.addCard(Card.of(i / 2), 3);
            } else {
                builder.addCardToSkat(Card.of(i / 2));
            }
        }

        return builder.build();
    }

    public static Game.Builder builder() {
        return new Game.Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return _player1Front.equals(game._player1Front) && _player2Middle.equals(game._player2Middle) && _player3Rear.equals(game._player3Rear) && _skat.equals(game._skat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_player1Front, _player2Middle, _player3Rear, _skat);
    }

    /**
     * Generates a random Game object using the current time in millis as a seed for the Random object.
     *
     * @return a random Game object
     */
    public static Game random() {
        return random(System.currentTimeMillis());
    }

    /**
     * Generates a random Game object from the given seed. Using the same seed will result in the same Game object.
     *
     * @param seed a seed for the used Random initialization
     * @return a Game object
     */
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

        private transient final Set<Card> _all = new HashSet<Card>() {
            @Override
            public boolean add(Card card) {
                boolean add = super.add(card);
                if (!add) {
                    throw new IllegalArgumentException(String.format("Card %s is already present.", card));
                }
                return true;
            }
        };

        /**
         * @param card   a card that is not yet present in this builder object.
         * @param player one of {1,2,3}
         * @return the current builder object
         * @throws IllegalArgumentException if the value for <code>player</code> is invalid.
         */
        public Builder addCard(Card card, int player) {
            switch (player) {
                case 1:
                    addToPlayer(_player1, card, PLAYER_FRONT);
                    break;
                case 2:
                    addToPlayer(_player2, card, PLAYER_MIDDLE);
                    break;
                case 3:
                    addToPlayer(_player3, card, PLAYER_REAR);
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Player %s does not exist.", player));
            }
            return this;
        }

        public Builder addCard(Card card) {
            switch (_all.size() / 10) {
                case 0:
                    addToPlayer(_player1, card, PLAYER_FRONT);
                    break;
                case 1:
                    addToPlayer(_player2, card, PLAYER_MIDDLE);
                    break;
                case 2:
                    addToPlayer(_player3, card, PLAYER_REAR);
                    break;
                case 3:
                    addCardToSkat(card);
                    break;
            }
            return this;
        }

        private void addToPlayer(List<Card> cardList, Card card, String player) {
            checkSizeBeforeAdding(cardList, 10, player + "-" + PLAYER);
            _all.add(card);
            cardList.add(card);
        }

        public Builder addCardToSkat(Card card) {
            checkSizeBeforeAdding(_skat, 2, SKAT);
            _all.add(card);
            _skat.add(card);
            return this;
        }

        private static void checkSizeBeforeAdding(List<Card> cardList, int maximumAmount, String destination) {
            if (cardList.size() == maximumAmount) {
                throw new IllegalArgumentException("Cannot add any more cards. Have already " + maximumAmount + " cards for " + destination + ".");
            }
        }

        public Game build() {
            if (_all.size() != 32) {
                throw new IllegalStateException("Game cannot be build. There are only " + _all + " card(s) set.");
            }
            return new Game(_player1, _player2, _player3, _skat);
        }

        public int cardsAdded() {
            return _player1.size() + _player2.size() + _player3.size() + _skat.size();
        }
    }
}
