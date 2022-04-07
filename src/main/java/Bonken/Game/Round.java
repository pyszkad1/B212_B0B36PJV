package Bonken.Game;

import java.util.ArrayList;

public class Round {
    CardHand[] cardHands;

    public Round() {
        Deck deck = new Deck(); //TODO later do in game!!!
        deck.shuffle();
        ArrayList<ArrayList<Card>> hands = deck.deal();
        System.out.println(hands.toString());
        cardHands = new CardHand[4];
        for (int i = 0; i < 4; i++) {
            cardHands[i] = new CardHand(hands.get(i));
        }

    }

    public void chooseGame() {
        //TODO
    }

    public void playRound() {
        int firstPlayer = 0; //TODO should be player -1 from chooser of minigame
        for (int i = 0; i < 13; i++) {
            firstPlayer = playTrick(firstPlayer);
            System.out.println("first player is " + firstPlayer);
        }

    }

    public int playTrick(int firstPlayer) {
        Trick trick = new Trick(firstPlayer, cardHands, 1);
        return trick.getTrickWinner(trick.getTrick());
    }



}
