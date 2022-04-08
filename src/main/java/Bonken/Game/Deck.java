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
        ArrayList<Card> cardHand = null;
        ArrayList<ArrayList<Card>> cardHands = new ArrayList<ArrayList<Card>>(4);
        int cnt = 0;
        for (Card card : cardDeck) {
            if(cardHand == null) {
                cardHand = new ArrayList<Card>(13);
                cnt = 0;
            }
            cardHand.add(card);
            cnt++;
            if (cnt == 13) {
                cardHands.add(cardHand);
                cardHand = null;
            }
        }
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
