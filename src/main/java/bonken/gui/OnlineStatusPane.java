package bonken.gui;

import bonken.game.Game;
import bonken.game.Minigames;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OnlineStatusPane extends VBox {

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

    private Label minigameLabel , roundLabel, roundHeader, scoreLabel;
    private HBox minigameBox, roundBox;
    private VBox scoreboardBox;
    private OnlineScoreboardView onlineScoreboardView;

    public OnlineStatusPane() {
        super();

        roundHeader= new Label("Round ");
        roundLabel= new Label();
        roundBox = new HBox(roundHeader, roundLabel);

        minigameLabel= new Label() ;
        minigameBox = new HBox(minigameLabel);

        scoreLabel = new Label("Score");
        onlineScoreboardView = new OnlineScoreboardView();
        scoreLabel.hoverProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal) {
                onlineScoreboardView.show();

            } else {
                onlineScoreboardView.hide();
            }
        });
        scoreboardBox = new VBox(scoreLabel, onlineScoreboardView);

        this.getChildren().addAll(roundBox, minigameBox, scoreboardBox);

    }

    public void update(String roundNum, String minigame) {
        roundLabel.setText(roundNum + " / 11");
        minigameLabel.setText(minigame);
    }
}
