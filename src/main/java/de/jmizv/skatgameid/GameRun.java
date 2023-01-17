package de.jmizv.skatgameid;

import java.util.Arrays;
import java.util.Base64;
import java.util.BitSet;
import java.util.Optional;

public class GameRun {

    public static final int[] BID_VALUES = { // 73 values
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

    public GameRun(Game game, GameType gameType, int[] bids, Card[] skatCards, int[][] cardsPlayed) {
        _game = game;
        _gameType = gameType;
        _bids = bids;
        _skatCards = skatCards;
        _cardsPlayed = cardsPlayed;
    }

    public GameType gameType() {
        return _gameType;
    }

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
        return new Card[]{_game.getPlayerFront().get(index),
                _game.getPlayerMiddle().get(index),
                _game.getPlayerRear().get(index)};
    }

    /**
     * @param index
     * @return
     */
    public int[] trickAsIndexes(int index) {
        int[] trick = _cardsPlayed[index];
        return Arrays.copyOf(trick, trick.length);
    }

    public String computeId() {
        BitSet bitset = new BitSet(64 + 158);
        // the first 64 bits for the game, i.e. the card distribution before bidding.
        bitset.or(_game.computeIdAsBitSet());
        // set the cards that are in the Skat when the game starts.
        setBitSet(bitset, _skatCards[0].number(), 64, 5);
        setBitSet(bitset, _skatCards[1].number(), 69, 5);

        setBitSet(bitset, _bids[0],74, 7);
        setBitSet(bitset, _bids[1],81, 7);
        setBitSet(bitset, _bids[2],88, 7);

        setBitSet(bitset, _gameType.gameTypeKind().ordinal(), 95,3);
        bitset.set(98, _gameType.isHand());
        bitset.set(99, _gameType.isOuvert());
        bitset.set(100, _gameType.isHandAnnounced());
        bitset.set(101, _gameType.isOuvertAnnounced());


        return Base64.getEncoder().encodeToString(bitset.toByteArray());
    }

    private static void setBitSet(BitSet bitSet, int number, int startingIndex, int maxLength) {
        for (int idx = 0; idx < maxLength; ++idx) {
            if ((number & (0x01 << idx)) != 0) {
                bitSet.set(startingIndex + maxLength - idx);
            }
        }
    }

    public Game game() {
        return _game;
    }
}
