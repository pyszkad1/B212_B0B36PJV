package bonken;

import bonken.game.Card;
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
        offlineGameView.initGameView(game ,stage, this);
        //game.startGameOffline();
        //game.startRoundOffline();
        //game.round.chooseGame();

        //FOR LOOP on every minigame!
        //playRound();
    }

    public void playRound() {
        Card card = new Card(2, 0);
        //for (int i = 0; i < 13; i++) {
            offlineGameView.TrickView(stage, game, card);
        System.out.println("I am here");
        //}


    }
}
