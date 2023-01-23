package de.jmizv.skatgameid;

import java.math.BigInteger;
import java.util.*;

public class GameRun {

    /**
     * All the possible bid values in a Skat game.
     */
    private static final int[] BID_VALUES = { // 73 values
            0, 18, 20, 22, 23, 24, 27, 30, 33, 35, 36, 40, 44, 45, 46, 48,
            50, 54, 55, 59, 60, 63, 66, 70, 72, 77, 80, 81, 84, 88, 90, 96,
            99, 100, 108, 110, 117, 120, 121, 126, 130, 132, 135, 140, 143,
            144, 150, 153, 154, 156, 160, 162, 165, 168, 170, 171, 176, 180,
            187, 189, 190, 192, 198, 200, 204, 207, 209, 210, 216, 220, 228,
            240, 264};

    private final Game _game;
    private final GameType _gameType;
    private final int[] _bids; // 3 values
    // the cards that have been put to the skat or the cards that were initially in the skat when
    // the player plays "Hand".
    private final Card[] _skatCards; // values
    //
    private final int[][] _cardsPlayed;

    public GameRun(Game game, int[] bids, Card[] skatCards, GameType gameType, int[][] cardsPlayed) {
        _game = Objects.requireNonNull(game, "The game cannot be null.");
        _gameType = Objects.requireNonNull(gameType, "The game type cannot be null.");
        _bids = Objects.requireNonNull(bids, "The bids cannot be null.");
        _skatCards = Objects.requireNonNull(skatCards, "The skat cards cannot be null.");
        _cardsPlayed = Objects.requireNonNull(cardsPlayed, "The cards played cannot be null.");
    }

    /**
     * Returns a copy of all the bid values.
     *
     * @return int array with all the bid values
     */
    public static int[] bidValues() {
        return Arrays.copyOf(BID_VALUES, BID_VALUES.length);
    }

    public GameType gameType() {
        return _gameType;
    }

    /**
     * Computes which player has won the bidding. The optional is empty if every player passed, e.g. either Ramsch will be played or
     * the game get `eingepasst`.
     *
     * @return an Optional containing the solo player or empty if all player passed
     */
    public Optional<Player> soloPlayer() {
        if (_bids[0] == 0 && _bids[1] == 0 && _bids[2] == 0) {
            return Optional.empty();
        }
        if (_bids[0] > _bids[1] && _bids[0] > _bids[2]) {
            return Optional.of(Player.FOREHAND);
        }
        if (_bids[1] > _bids[2]) {
            return Optional.of(Player.MIDHAND);
        }
        return Optional.of(Player.REARHAND);
    }

    /**
     * Returns the highest bid of an individual player. This could be 0 if they passed or 18, 20, etc.
     *
     * @param player the bid of which player should be returned
     * @return the bid of the given player
     */
    public int bid(Player player) {
        return _bids[player.ordinal()];
    }

    public Card skatCard(int no) {
        return _skatCards[no];
    }

    /**
     * @param index
     * @return
     */
    public Card[] trickAsCards(int index) {
        return new Card[]{_game.frontCards().get(index),
                _game.middleCards().get(index),
                _game.rearCards().get(index)};
    }

    /**
     * @param index
     * @return
     */
    public int[] trickAsIndexes(int index) {
        int[] trick = _cardsPlayed[index];
        return Arrays.copyOf(trick, trick.length);
    }

    public static GameRun ofId(String id) {
        if (id.length() < 2) {
            throw new IllegalArgumentException("Game run id \"" + id + "\" is too short.");
        }
        BitSet bitSet = BitSet.valueOf(Base64.getDecoder().decode(id));
        if (bitSet.size() < 64 + 158) {
            throw new IllegalArgumentException("Game run id is not compatible for decoding.");
        }

        Game game = Game.ofBitSet(bitSet.get( 0, 64));
        int index = 64;
        // the bids of the players
        int[] bids = new int[]{
                Util.integerFromBitSet(bitSet, index, 7),
                Util.integerFromBitSet(bitSet, index + 7, 7),
                Util.integerFromBitSet(bitSet, index + 14, 7),
        };
        index += 21;

        // the dropped cards of the solo player
        Card card1 = Card.of(Util.integerFromBitSet(bitSet, index, 5));
        index += 5;
        Card card2 = Card.of(Util.integerFromBitSet(bitSet, index, 5));
        index += 5;

        // the announced game type
        GameType gameType = GameType.of(GameTypeKind.of(Util.integerFromBitSet(bitSet, index, 3)),
                bitSet.get(index + 3),
                bitSet.get(index + 4),
                bitSet.get(index + 5),
                bitSet.get(index + 6));
        index += 7;

        int[][] cardsPlayed = new int[10][];
        for (int i = 0; i < 10; ++i) {
            cardsPlayed[i] = new int[3];
            for (int j = 0; j < 3; ++j) {
                cardsPlayed[i][j] = Util.integerFromBitSet(bitSet, index, 4);
                index += 4;
            }
        }

        return new GameRun(game, bids, new Card[]{card1, card2}, gameType, cardsPlayed);
    }

    public BigInteger computeIdAsBigInteger() {
        return new BigInteger(computeIdAsBitSet().toByteArray());
    }

    public String computeId() {
        return Base64.getEncoder().encodeToString(computeIdAsBitSet().toByteArray());
    }

    BitSet computeIdAsBitSet() {
        BitSet bitset = new BitSet(64 + 158);
        // the first 64 bits for the game, i.e. the card distribution before bidding.
        bitset.or(_game.computeIdAsBitSet());
        int index = 64;

        Util.setBitSet(bitset, _bids[0], index, 7);
        index += 7;
        Util.setBitSet(bitset, _bids[1], index, 7);
        index += 7;
        Util.setBitSet(bitset, _bids[2], index, 7);
        index += 7;

        // set the cards that are in the Skat when the game starts.
        Util.setBitSet(bitset, _skatCards[0].number(), index, 5);
        index += 5;
        Util.setBitSet(bitset, _skatCards[1].number(), index, 5);
        index += 5;

        // the game type kind
        Util.setBitSet(bitset, _gameType.gameTypeKind().ordinal(), index, 3);
        index += 3;
        bitset.set(index++, _gameType.isHand());
        bitset.set(index++, _gameType.isOuvert());
        bitset.set(index++, _gameType.isHandAnnounced());
        bitset.set(index++, _gameType.isOuvertAnnounced());

        for (int[] trick : _cardsPlayed) {
            for (int cardIndex : trick) {
                Util.setBitSet(bitset, cardIndex, index, 4);
                index += 4;
            }
        }

        return bitset;
    }

    public Game game() {
        return _game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRun gameRun = (GameRun) o;
        return _game.equals(gameRun._game)
                && _gameType.equals(gameRun._gameType)
                && Arrays.equals(_bids, gameRun._bids)
                && Arrays.deepEquals(_skatCards, gameRun._skatCards)
                && Arrays.deepEquals(_cardsPlayed, gameRun._cardsPlayed);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(_game, _gameType);
        result = 31 * result + Arrays.hashCode(_bids);
        result = 31 * result + Arrays.hashCode(_skatCards);
        result = 31 * result + Arrays.hashCode(_cardsPlayed);
        return result;
    }
}
