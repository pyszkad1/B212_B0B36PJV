package game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class CardHand {
    ArrayList<Card> hand;
    ArrayList<Card> playableCards;

    public CardHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public void sortHand() {
        hand.sort(Comparator.comparing(Card::getRank));
        hand.sort(Comparator.comparing(Card::getSuit));
    }

    public ArrayList<Card> getPlayableCards(Card firstCard) {
        if (firstCard.getSuit() == -1) {
            playableCards = hand;
        }
        playableCards = hasThisSuit(firstCard);
        if (playableCards.size() == 0) {
            playableCards = hand;
        }
        return playableCards;
    }

    public ArrayList<Card> hasThisSuit(Card card){
        ArrayList<Card> ret = new ArrayList<Card>();
        for (int i = 0; i < hand.size(); i++) {
            if (card.getSuit() == hand.get(i).getSuit()) {
                ret.add(hand.get(i));
            }
        }
        return ret;
    }

    @Override
    public String toString() {
        String ret = "";
        int cnt = 0;
        for (Card card : hand) {
            ret += card.toString();
            if (cnt >= 0 && cnt < 51) {
                ret += ", ";
            }
            cnt++;
        }
        return ret;
    }


}
