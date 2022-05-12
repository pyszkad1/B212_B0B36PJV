package bonken.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    PlayerInterface[] players;
    Deck deck;
    ArrayList<Integer> minigames = new ArrayList<>(12);
    ScoreBoard scoreBoard;
    public boolean gameEnded;
    public ArrayList<Round> rounds;


    public ArrayList<Integer> getMinigames() {
        return minigames;
    }

    public PlayerInterface[] getPlayers() {
        return players;
    }

    public Round getCurrentRound() {
        return this.rounds.get(rounds.size() - 1);
    }

    public Game( PlayerInterface[] players) {
        for (int i = 0; i < 12; i++) {
            minigames.add(Integer.valueOf(i));
        }

        this.players = players;

        Random random = new Random();
        int startingPlayer = 0 ;// random.nextInt( 4);

        players[startingPlayer].setHisTurn(true);

        rounds = new ArrayList<>();
        scoreBoard = new ScoreBoard(players);
        deck = new Deck();
        gameCounter = 0;
    }

    private int gameCounter;

    public void startRound() {
        deck.shuffle();
        Round round = new Round(this, deck, minigames, players);
        rounds.add(round);
        System.out.println("--------------------------------------------------------------------------------------------");
        round.playRound(() -> finishRound());
    }

    private void finishRound()
    {
        System.out.println(scoreBoard.toString());

        if(++gameCounter == 11) {
            gameEnded = true;
            System.out.println("End of Game");
            return;
        }

        startRound();
    }

}
