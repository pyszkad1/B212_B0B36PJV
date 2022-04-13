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
            cardHands[i].sortHand();
        }


    }

    public void chooseGame() {
        //TODO
    }

    public void playRound() {
        int firstPlayer = 0; //TODO should be player -1 from chooser of minigame
        int[] tricksTaken = {0,0,0,0};
        for (int i = 0; i < 13; i++) {
            firstPlayer = playTrick(firstPlayer);
            System.out.println("first player is " + firstPlayer);
            tricksTaken[firstPlayer]++;
        }

        //QUICK COUNTER! - deletable
        int maxTricks = 0;
        int playerNumber = -1;
        System.out.println("Tricks of players:");
        for (int i = 0; i < 4; i++) {
            System.out.println("Player " + i + " got " + tricksTaken[i]);
            if (tricksTaken[i] > maxTricks){
                maxTricks = tricksTaken[i];
                playerNumber = i;
            }
        }
        System.out.println("Player " + playerNumber + " won with " + maxTricks + " tricks");

    }

    public int playTrick(int firstPlayer) {
        Trick trick = new Trick(firstPlayer, cardHands, 1); //TODO get trumps from minigame choice
        return trick.getTrickWinner(trick.getTrick());
    }
}

