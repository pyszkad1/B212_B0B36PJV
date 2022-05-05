package game;

import java.util.ArrayList;
import java.util.Scanner;

public class PlayerBot implements PlayerInterface {
    final String username;
    final int id;
    CardHand cardHand;
    ArrayList<Card> playableCards;
    int score;
    boolean hisTurn;

    public PlayerBot(String username, int id) {
        super();
        this.username = username;
        this.id = id;
    }

    @Override
    public Card play(Card card) {
        // TODO play card based on chosen minigame
        playableCards = cardHand.getPlayableCards(card);
        System.out.println(playableCards);
        System.out.println("choosing from " + (playableCards.size()) + " cards");
        int playedCard = 0;
        Card tmp = playableCards.get(playedCard);
        this.cardHand.hand.remove(tmp);
        return tmp;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean isHisTurn() {
        return hisTurn;
    }

    @Override
    public void setHisTurn(boolean hisTurn) {
        this.hisTurn = hisTurn;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void setCardHand(CardHand cardHand) {
        this.cardHand = cardHand;
    }
}
