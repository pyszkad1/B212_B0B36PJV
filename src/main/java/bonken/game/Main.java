package bonken.game;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(Integer.parseInt(args[0]));                // TODO change config
        game.startGame();

    }
}
