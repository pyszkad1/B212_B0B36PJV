package Bonken.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    List<Card> cardDeck = new ArrayList<Card>(52);

    public Deck() {
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < 15; j++) {
                Card card = new Card(j, i);
                cardDeck.add(card);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cardDeck);
    }

    public ArrayList<ArrayList<Card>> deal() {
        ArrayList<Card> cardHand = new ArrayList<Card>(13);
        ArrayList<ArrayList<Card>> cardHands = new ArrayList<ArrayList<Card>>(4);
        int cnt = 0;
        for (Card card : cardDeck) {
            cardHand.add(card);
            if (cnt < 12) {
                cnt++;
            } else {
                cardHands.add(cardHand);
                cardHand = new ArrayList<Card>(13);
                cnt = 0;
            }
        }
        cardHands.add(cardHand);
        return cardHands;
    }

    @Override
    public String toString() {
        String ret = "";
        int cnt = 0;
        for (Card card : cardDeck) {
            ret += card.toString();
            if (cnt >= 0 && cnt < 51) {
                ret += ", ";
            }
            cnt++;
        }
        return ret;
    }
}
