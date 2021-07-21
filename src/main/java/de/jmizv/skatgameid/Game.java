package de.jmizv.skatgameid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private final List<Card> _player1 = new ArrayList<>(); // 10 cards
    private final List<Card> _player2 = new ArrayList<>(); // 10 cards
    private final List<Card> _player3 = new ArrayList<>(); // 10 cards
    private final List<Card> _trick = new ArrayList<>(); // 2 cards

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

    public void addCard(Card card, int player) {
        _all.add(card);
        switch (player) {
            case 1 -> addToPlayer(_player1, card);
            case 2 -> addToPlayer(_player2, card);
            case 3 -> addToPlayer(_player3, card);
            default -> throw new IllegalArgumentException(String.format("Player %s does not exist.", player));
        }
    }

    private static void addToPlayer(List<Card> cardList, Card card) {
        checkSizeBeforeAdding(cardList, 10);
        cardList.add(card);
    }

    public void addCardToTrick(Card card) {
        checkSizeBeforeAdding(_trick, 2);
        _all.add(card);
        _trick.add(card);
    }

    private static void checkSizeBeforeAdding(List<Card> cardList, int maximumAmount) {
        if (cardList.size() == maximumAmount) {
            throw new IllegalArgumentException("Cannot add any more card. Have already " + maximumAmount);
        }
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

    public static class Builder {

        public Game build() {
            return null;
        }
    }
}
