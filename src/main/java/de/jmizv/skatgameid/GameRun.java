package de.jmizv.skatgameid;

import java.util.Base64;
import java.util.BitSet;
import java.util.Optional;

public class GameRun {

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

    public Card[] trick(int index) {
        return new Card[]{_game.getPlayerFront().get(index),
                _game.getPlayerMiddle().get(index),
                _game.getPlayerRear().get(index)};
    }

    public String computeId() {
        BitSet bitset = new BitSet(92);
        // the first 64 bits for the game, i.e. the card distribution before bidding.
        bitset.or(_game.computeIdAsBitSet());

        bitset.set(92);

        return Base64.getEncoder().encodeToString(bitset.toByteArray());
    }
}
