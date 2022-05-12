package bonken.gui;

import bonken.game.Game;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatusPane extends VBox {

    Game game;

    /**
     * SHOULD SHOW
     *
     * Current minigame
     * Round
     * Score
     *
     *
     * */

    private Label minigameLabel, minigameHeader , roundLabel, roundHeader, scoreLabel, scoreHeader;

    public StatusPane(Game game) {
        super();

        this.game = game;

        minigameHeader = new Label("Minigame") ;

        minigameLabel= new Label() ;

        roundHeader= new Label("Round") ;

        roundLabel= new Label() ;

        scoreHeader = new Label("Score");

        scoreLabel= new Label() ;

    }

    public void update() {

    }
}
