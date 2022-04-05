package Bonken.Game;

import java.util.ArrayList;

public class CardHand {
    ArrayList<Card> hand;
    ArrayList<Card> playableCards;

    public CardHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public ArrayList<Card> getPlayableCards(Card firstCard) {
        playableCards = hasThisSuit(firstCard);
        if (playableCards.size() == 0){
            playableCards = hand;
        }

        return playableCards;
    }

    public ArrayList<Card> hasThisSuit(Card card){
        ArrayList<Card> ret = new ArrayList<Card>();
        for (int i = 0; i < hand.size(); i++) {
            if (card.suit == hand.get(i).suit){
                ret.add(hand.get(i));
            }
        }
        return ret;
    }

    public Card play() {
        return playableCards.get(playableCards.size()-1);
    }

}
