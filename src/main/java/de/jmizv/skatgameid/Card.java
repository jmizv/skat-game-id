package de.jmizv.skatgameid;

import java.util.Locale;
import java.util.Objects;

public class Card implements Comparable<Card> {

    private final Suit _suit;
    private final Value _value;

    public Card(Suit suit, Value value) {
        _suit = Objects.requireNonNull(suit);
        _value = Objects.requireNonNull(value);
    }

    public Suit getSuit() {
        return _suit;
    }

    public Value getValue() {
        return _value;
    }

    @Override
    public String toString() {
        return "" + getSuit() + getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return _suit == card._suit && _value == card._value;
    }

    public int number() {
        return _suit.ordinal() * 8 + _value.ordinal();
    }

    /**
     * Tests whether this card can be played upon the given other card with the given game type. This mean if this card
     * "bedient" the other, given card.
     *
     * @param otherCard card on which this card should be played
     * @param gameType  the game type
     * @return true if this card can be played on the given card
     */
    public boolean canBePlayedUpon(Card otherCard, GameTypeKind gameType) {
        if (this.equals(otherCard)) {
            throw new IllegalArgumentException("Cannot check for played upon with the same two cards.");
        }
        switch (gameType) {
            case NULL:
                // with NULL game the colour must match
                return getSuit() == otherCard.getSuit();

            case GRAND: {
                if (getValue() == Value.JACK && otherCard.getValue() == Value.JACK) {
                    return true;
                }
                if (getValue() == Value.JACK || otherCard.getValue() == Value.JACK) {
                    return false;
                }
                return getSuit() == otherCard.getSuit();
            }
            case DIAMONDS:
            case HEARTS:
            case SPADES:
            case CLUBS:
                if (otherCard.isTrump(gameType)) {
                    return isTrump(gameType);
                }
                if (isTrump(gameType)) {
                    return false;
                }
                return getSuit() == otherCard.getSuit();
            default:
                throw new UnsupportedOperationException("Unsupported value: " + gameType);
        }
    }

    public boolean isTrump(GameTypeKind gameTypeKind) {
        if (gameTypeKind == GameTypeKind.NULL) {
            // with NULL game there are no trump cards
            return false;
        }
        if (getValue() == Value.JACK) {
            // with non-NULL game a JACK is always a trump
            return true;
        }
        if (gameTypeKind == GameTypeKind.GRAND) {
            return false;
        }
        return isGameTypeKindSameAsSuit(gameTypeKind, getSuit());
    }

    private static boolean isGameTypeKindSameAsSuit(GameTypeKind gameTypeKind, Suit suit) {
        if (suit == Suit.CLUBS && gameTypeKind == GameTypeKind.CLUBS) {
            return true;
        }
        if (suit == Suit.DIAMONDS && gameTypeKind == GameTypeKind.DIAMONDS) {
            return true;
        }
        if (suit == Suit.SPADES && gameTypeKind == GameTypeKind.SPADES) {
            return true;
        }
        return suit == Suit.HEARTS && gameTypeKind == GameTypeKind.HEARTS;
    }

    public static Card of(int no) {
        return new Card(Suit.of(no / 8), Value.of(no % 8));
    }

    @Override
    public int hashCode() {
        return Objects.hash(_suit, _value);
    }

    public static Card of(String value) {
        if (value.length() != 2) {
            throw new IllegalArgumentException("Cannot create a Card of value \"" + value + "\" as it does not have exactly two characters.");
        }
        String[] split = value.toUpperCase(Locale.ROOT).split("");
        return new Card(Suit.of(split[0]), Value.of(split[1]));
    }

    @Override
    public int compareTo(Card o) {
        return number() - o.number();
    }

    /**
     * Checks whether this card would beat the given card in a game.
     *
     * @param gameTypeKind the current game type
     * @param card         the card that should be beaten
     * @return true if this card would be the given card in the given game
     */
    public boolean beats(GameTypeKind gameTypeKind, Card card) {
        if (this.equals(card)) {
            throw new IllegalArgumentException("Can not check \"beats\" to the same card.");
        }
        switch (gameTypeKind) {
            case NULL:
                if (getSuit() == card.getSuit()) {
                    return getValue().ordinal() - card.getValue().ordinal() > 0;
                }
                return false;
            case GRAND: {
                if (getValue() == Value.JACK && card.getValue() == Value.JACK) {
                    return getSuit().ordinal() - card.getSuit().ordinal() > 0;
                }
                if (getValue() == Value.JACK) {
                    return true;
                }
                if (card.getValue() == Value.JACK) {
                    return false;
                }
                if (getSuit() == card.getSuit()) {
                    return getValue().ordinal() - card.getValue().ordinal() > 0;
                }
                return false;
            }
            case DIAMONDS:
            case HEARTS:
            case SPADES:
            case CLUBS: {
                if (getValue() == Value.JACK && card.getValue() == Value.JACK) {
                    return getSuit().ordinal() - card.getSuit().ordinal() > 0;
                }
                if (getValue() == Value.JACK) {
                    return true;
                }
                if (card.getValue() == Value.JACK) {
                    return false;
                }
                if (isTrump(gameTypeKind) && card.isTrump(gameTypeKind)) {
                    return getValue().ordinal() - card.getValue().ordinal() > 0;
                }
                if (isTrump(gameTypeKind)) {
                    return true;
                }
                if (card.isTrump(gameTypeKind)) {
                    return false;
                }
                if (getSuit() == card.getSuit()) {
                    return getValue().ordinal() - card.getValue().ordinal() > 0;
                }
                return false;
            }
            default:
                throw new IllegalStateException("Game type: " + gameTypeKind + " not supported.");
        }
    }

    public String toFancyString() {
        String result;
        switch (_suit) {
            case DIAMONDS:
                result = "♦";
                break;
            case HEARTS:
                result = "♥";
                break;
            case SPADES:
                result = "♠";
                break;
            case CLUBS:
                result = "♣";
                break;
            default:
                throw new IllegalStateException("Illegal suit: " + _suit);
        }
        switch (_value) {
            case ACE:
                result += "A";
                break;
            case TEN:
                result += "10";
                break;
            case NINE:
                result += "9";
                break;
            case EIGHT:
                result += "8";
                break;
            case SEVEN:
                result += "7";
                break;
            case JACK:
                result += "J";
                break;
            case QUEEN:
                result += "Q";
                break;
            case KING:
                result += "K";
                break;
            default:
                throw new IllegalStateException("Illegal value: " + _value);
        }
        return result;
    }
}