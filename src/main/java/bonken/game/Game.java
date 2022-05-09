package bonken.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    PlayerInterface[] players;
    Deck deck;
    ArrayList<Integer> minigames = new ArrayList<>(12);
    ScoreBoard scoreBoard;
    int numOfPlayers;
    boolean gameEnded;

    public Game(int numOfPlayers) {
        for (int i = 0; i < 12; i++) {
            minigames.add(Integer.valueOf(i));
        }
        this.numOfPlayers = numOfPlayers;
        getPlayers();


        scoreBoard = new ScoreBoard(players);
    }

    public void startGame() {
        deck = new Deck();
        for (int i = 0; i < 11; i++) {
            deck.shuffle();
            Round round = new Round(this, deck, minigames, players);
            System.out.println("--------------------------------------------------------------------------------------------");
            round.playRound();
            System.out.println(scoreBoard.toString());
        }

        gameEnded = true;
        System.out.println("End of game.");
    }

    public void getPlayers() {
        System.out.println("Enter names of four players:");
        System.out.println("num of players is " + numOfPlayers );
        players = new PlayerInterface[4];
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int startingPlayer = random.nextInt( 4);
        for (int i = 0; i < numOfPlayers; i++) {
            players[i] = new Player(scanner.next(), i);
            if (i == startingPlayer) {
                players[i].setHisTurn(true);
            }
        }
        //TODO one for loop
        for (int i = numOfPlayers; i < 4; i++) {
            players[i] = new PlayerBot("Bot" + i, i);
            if (i == startingPlayer) {
                players[i].setHisTurn(true);
            }
        }

    }
}
