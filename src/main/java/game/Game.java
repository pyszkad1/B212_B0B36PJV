package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    Player[] players;
    Deck deck;
    ArrayList<Integer> minigames = new ArrayList<>(12);
    ScoreBoard scoreBoard;
    int numOfPlayers;

    public Game(int numOfPlayers) {
        for (int i = 0; i < 12; i++) {
            minigames.add(i);
        }
        this.players = getPlayers();
        this.numOfPlayers = numOfPlayers;

        scoreBoard = new ScoreBoard(players);
    }

    public void startGame() {
        deck = new Deck();
        for (int i = 0; i < 12; i++) {
            deck.shuffle();
            Round round = new Round(this, deck, minigames, players);
            round.playRound();
            System.out.println(scoreBoard.toString());
        }
        System.out.println("Konec");
    }

    public PlayerInterface[] getPlayers() {
        System.out.println("Enter names of four players:");
        PlayerInterface[] players = new PlayerInterface[4];
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int startingPlayer = random.nextInt( 4);
        for (int i = 0; i < numOfPlayers; i++) {
            players[i] = new Player(scanner.next(), i);
            if (i == startingPlayer) {
                players[i].hisTurn = true;
            }
        }
        return players;
    }
}
