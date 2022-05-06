package game;

import java.util.ArrayList;

public class MiniGameNegative {

    ArrayList<Card> penaltyCards;
    final int miniGameNum;
    Deck deck;
    public final String[] negativeNames = new String[]
            {"Kings and Jacks", "Queens", "King of Hearts", "No Hearts", "No Tricks", "Last Trick", "Beer Card"};

    public MiniGameNegative(int miniGameNum, Deck deck) {
        this.miniGameNum = miniGameNum;
        this.deck = deck;
        penaltyCards = new ArrayList<>();
        getPenaltyCards();
    }

    private void getPenaltyCards() {
        switch (miniGameNum) {
            case 0:
                for (Card card : deck.cardDeck) {
                    if (card.getRank() == 13 || card.getRank() == 11) {
                        penaltyCards.add(card);
                    }
                }
                break;

            case 1:
                for (Card card : deck.cardDeck) {
                    if (card.getRank() == 12) {
                        penaltyCards.add(card);
                    }
                }
                break;

            case 2:
                for (Card card : deck.cardDeck) {
                    if (card.getRank() == 13 && card.getSuit() == 2) {
                        penaltyCards.add(card);
                    }
                }
                break;

            case 3:
                for (Card card : deck.cardDeck) {
                    if (card.getSuit() == 2) {
                        penaltyCards.add(card);
                    }
                }
                break;

            case 6:
                for (Card card : deck.cardDeck) {
                    if (card.getSuit() == 1 && card.getRank() == 7) {
                        penaltyCards.add(card);
                    }
                }
                break;

            default:
                break;
        }

    }
}
