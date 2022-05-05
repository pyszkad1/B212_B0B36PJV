package game;

import java.util.ArrayList;
import java.util.Scanner;

public class Player implements PlayerInterface {
    final String username;
    final int id;
    private CardHand cardHand;
    ArrayList<Card> playableCards;
    int score;
    boolean hisTurn;

    public Player(String username, int id) {
        this.username = username;
        this.id = id;
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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Card play(Card card) {
        //TODO
        playableCards = cardHand.getPlayableCards(card);
        System.out.println(playableCards);
        System.out.println("choosing from " + (playableCards.size()) + " cards");
        Scanner scanner = new Scanner(System.in);
        int playedCard = scanner.nextInt();
        while (playedCard > playableCards.size()-1 || playedCard < 0) {
            System.out.println("not a card, please choose again");
            playedCard = scanner.nextInt();
        }
        Card tmp = playableCards.get(playedCard);
        this.cardHand.hand.remove(tmp);
        return tmp;
    }

}
