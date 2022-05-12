package bonken.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class CardHand {
    ArrayList<Card> hand;


    public CardHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public void sortHand() {
        hand.sort(Comparator.comparing(Card::getRank));
        hand.sort(Comparator.comparing(Card::getSuit));
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getPlayableCards(Card firstCard) {
        ArrayList<Card> playableCards;
        if (firstCard.getSuit() == -1) {
            playableCards = new ArrayList<>(hand) ;
        }
        playableCards = hasThisSuit(firstCard);
        if (playableCards.size() == 0) {
            playableCards = new ArrayList<>(hand);
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
