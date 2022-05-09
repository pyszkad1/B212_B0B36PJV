package bonken;

import bonken.game.Game;
import bonken.gui.OfflineGameView;
import javafx.stage.Stage;

public class Controller {
    OfflineGameView offlineGameView;
    Game game;
    Stage stage;

    public Controller(Stage stage) {
        this.stage = stage;
    }

    public void startGame() {
        game = new Game(1);
        offlineGameView = new OfflineGameView();
        offlineGameView.initGameView(game ,stage);
        game.startGameOffline();

        //FOR LOOP on every minigame!
        playRound();
    }

    public void playRound() {


    }
}
