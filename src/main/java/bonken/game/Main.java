package bonken.game;

import bonken.utils.Callable;
import javafx.application.Platform;

public class Main {

    public static void main(String[] args) {
        int numPlayers = Integer.parseInt(args[0]);

        PlayerInterface[] players = new PlayerInterface[4];

        for (int i = 0; i < 4; i++) {
            if (i < numPlayers)
            {
                players[i] = new ConsolePlayer(i, Position.values()[i]);
            } else {
                players[i] = new PlayerBot(i, Position.values()[i]);
            }
        }

        Game game = new Game(players, () -> Platform.exit(), false);

        game.startRound();

    }
}
