package bonken.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * General class for Deck object.
 */
public class Deck {
    ArrayList<Card> cardDeck = new ArrayList<Card>(52);

    public static final String[] png = new String[]
            {"2c.png", "3c.png", "4c.png", "5c.png", "6c.png", "7c.png", "8c.png", "9c.png", "10c.png", "jc.png", "qc.png", "kc.png", "ac.png",
                    "2d.png", "3d.png","4d.png","5d.png","6d.png","7d.png","8d.png","9d.png","10d.png","jd.png","qd.png","kd.png","ad.png",
                    "2h.png","3h.png","4h.png","5h.png","6h.png","7h.png","8h.png","9h.png","10h.png","jh.png","qh.png","kh.png","ah.png",
                    "2s.png","3s.png","4s.png","5s.png","6s.png","7s.png","8s.png","9s.png","10s.png","js.png","qs.png","ks.png","as.png"};

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

    public Card getSpecificCardByString(String string){
        int cardNum = 0;
        for (int i = 0; i < 52; i++) {
            if (string.equals(Deck.png[i])){
                cardNum = i;
            }
        }
        int suit = -1;
        if (cardNum < 13){
            suit = 0;
        }
        else if (cardNum < 26){
            suit = 1;
        }
        else if (cardNum < 39){
            suit = 2;
        }
        else if (cardNum < 52){
            suit = 3;
        }

        int rank = (cardNum % 13) + 2;

        Card retCard = new Card(2, -1);
        for (Card card: cardDeck) {
            if (card.getRank() == rank && card.getSuit() == suit){
                retCard = card;
            }
        }
        return retCard;

    }

    public ArrayList<Card> getCardDeck(){
        return cardDeck;
    }
}
