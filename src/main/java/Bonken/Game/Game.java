package Bonken.Game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    Player[] players;
    Deck deck;
    ArrayList<Integer> minigames = new ArrayList<>(12);

    public Game() {
        for (int i = 0; i < 12; i++) {
            minigames.add(i);
        }
        this.players = getPlayers();
    }

    public void startGame() {
        Deck deck = new Deck();
        for (int i = 0; i < 12; i++) {
            deck.shuffle();
            Round round = new Round(deck, minigames, players);
            round.playRound();
        }
        System.out.println("Konec");
    }

    public Player[] getPlayers() {
        Player[] players = new Player[4];
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int startingPlayer = random.nextInt(0, 4);
        for (int i = 0; i < 4; i++) {
            players[i] = new Player(scanner.next(), i);
            if (i == startingPlayer) {
                players[i].hisTurn = true;
            }
        }
        return players;
    }
}
