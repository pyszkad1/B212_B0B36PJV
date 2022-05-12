package bonken.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatusPane extends VBox {

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

    public StatusPane() {
        super();

        minigameHeader = new Label("Minigame") ;

        minigameLabel= new Label() ;

        roundHeader= new Label("Round") ;

        roundLabel= new Label() ;

        scoreHeader = new Label("Score");

        scoreLabel= new Label() ;

    }
}
