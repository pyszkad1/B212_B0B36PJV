package bonken.game;

import bonken.utils.Callable;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int numPlayers = Integer.parseInt(args[0]);

        Scanner scanner = new Scanner(System.in);

        PlayerInterface[] players = new PlayerInterface[4];



        for (int i = 0; i < 4; i++) {
            if (i < numPlayers)
            {
                players[i] = new ConsolePlayer(i, Position.values()[i]);
            } else {
                players[i] = new PlayerBot(i, Position.values()[i]);
            }
        }

        Game game = new Game(players, new Callable() {
            @Override
            public void call() {

            }
        });

        game.startRound();

    }
}
