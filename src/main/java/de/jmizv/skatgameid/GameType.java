package de.jmizv.skatgameid;

import java.util.Objects;

/**
 * Class represents the type of skat game.
 */
public class GameType {

    public static final String TRUE_AS_NUMBER = "1";
    private final GameTypeKind _gameTypeKind;
    private final boolean _hand; // 0
    private final boolean _ouvert; // 1
    private final boolean _handAnnounced; // 2
    private final boolean _ouvertAnnounced; // 3

    private GameType(GameTypeKind gameTypeKind) {
        this(gameTypeKind, false, false, false, false);
    }

    private GameType(GameTypeKind gameTypeKind, boolean hand, boolean ouvert, boolean handAnnounced, boolean ouvertAnnounced) {
        _gameTypeKind = gameTypeKind;
        _hand = hand;
        _ouvert = ouvert;
        _handAnnounced = handAnnounced;
        _ouvertAnnounced = ouvertAnnounced;
    }

    public GameTypeKind gameTypeKind() {
        return _gameTypeKind;
    }

    public boolean isHand() {
        return _hand;
    }

    public boolean isOuvert() {
        return _ouvert;
    }

    public boolean isHandAnnounced() {
        return _handAnnounced;
    }

    public boolean isOuvertAnnounced() {
        return _ouvertAnnounced;
    }

    public String toString() {
        return gameTypeKind().ordinal() + toString(_hand) + toString(_ouvert)
                + toString(_handAnnounced) + toString(_ouvertAnnounced);
    }

    private String toString(boolean boolValue) {
        return boolValue ? "1" : "0";
    }

    public static GameType of(GameTypeKind kind) {
        return new GameType(kind);
    }

    public static GameType of(GameTypeKind kind, boolean hand, boolean ouvert, boolean handAnnounced, boolean ouvertAnnounced) {
        return new GameType(kind, hand, ouvert, handAnnounced, ouvertAnnounced);
    }

    public static GameType of(String gameTypeAsString) {
        if (gameTypeAsString.length() == 1) {
            return new GameType(GameTypeKind.valueOf(gameTypeAsString));
        }
        String[] splitted = gameTypeAsString.split("");
        boolean hand = splitted.length >= 2 && TRUE_AS_NUMBER.equals(splitted[1]);
        boolean ouvert = splitted.length >= 3 && TRUE_AS_NUMBER.equals(splitted[2]);
        boolean handAnnounced = splitted.length >= 4 && TRUE_AS_NUMBER.equals(splitted[3]);
        boolean ouvertAnnounced = splitted.length >= 5 && TRUE_AS_NUMBER.equals(splitted[4]);
        return new GameType(GameTypeKind.of(splitted[0]), hand, ouvert, handAnnounced, ouvertAnnounced);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameType gameType = (GameType) o;
        return _hand == gameType._hand
                && _ouvert == gameType._ouvert
                && _handAnnounced == gameType._handAnnounced
                && _ouvertAnnounced == gameType._ouvertAnnounced
                && _gameTypeKind == gameType._gameTypeKind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_gameTypeKind, _hand, _ouvert, _handAnnounced, _ouvertAnnounced);
    }
}
