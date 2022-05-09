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
        startGame();
    }

    public void startGame() {
        game = new Game(1);
        offlineGameView = new OfflineGameView();
        offlineGameView.initGameView(game ,stage);
        //game.startGameOffline();
        //game.startRoundOffline();
        //game.round.chooseGame();

        //FOR LOOP on every minigame!
        //playRound();
    }

    public void playRound() {


    }
}
