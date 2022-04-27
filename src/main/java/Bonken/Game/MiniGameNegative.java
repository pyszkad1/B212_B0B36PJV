package Bonken.Game;

import java.util.ArrayList;

public class MiniGameNegative {

    ArrayList<Card> penaltyCards;
    final int miniGameNum;
    final Deck tempDeck;

    public MiniGameNegative(int miniGameNum) {
        this.miniGameNum = miniGameNum;
        tempDeck = new Deck();
        penaltyCards = new ArrayList<>();
        getPenaltyCards();
    }

    private void getPenaltyCards() {
        switch (miniGameNum) {
            case 0:
                for (Card card : tempDeck.cardDeck) {
                    if (card.getRank() == 13 || card.getRank() == 11) {
                        penaltyCards.add(card);
                    }
                }
                break;

            case 1:
                for (Card card : tempDeck.cardDeck) {
                    if (card.getRank() == 12) {
                        penaltyCards.add(card);
                    }
                }
                break;

            case 2:
                for (Card card : tempDeck.cardDeck) {
                    if (card.getRank() == 13 && card.getSuit() == 2) {
                        penaltyCards.add(card);
                    }
                }
                break;

            case 3:
                for (Card card : tempDeck.cardDeck) {
                    if (card.getSuit() == 2) {
                        penaltyCards.add(card);
                    }
                }
                break;
            case 6:
                for (Card card : tempDeck.cardDeck) {
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
