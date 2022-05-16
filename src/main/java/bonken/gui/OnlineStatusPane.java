package bonken.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OnlineStatusPane extends VBox {

    /**
     * SHOULD SHOW
     *
     * Current minigame
     * Round
     * Score
     *
     *
     * */

    public Label minigameLabel , roundLabel, roundHeader, scoreLabel;
    public HBox minigameBox, roundBox;
    public VBox scoreboardBox;
    public OnlineScoreboardView onlineScoreboardView;

    public OnlineStatusPane(OnlineScoreboardView onlineScoreboardView) {
        super();

        roundHeader = new Label("Round ");
        roundLabel = new Label();
        roundBox = new HBox(roundHeader, roundLabel);

        minigameLabel = new Label();
        minigameBox = new HBox(minigameLabel);

        scoreLabel = new Label("Score");
        this.onlineScoreboardView = onlineScoreboardView;
        scoreLabel.hoverProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                onlineScoreboardView.show();
            } else {
                onlineScoreboardView.hide();
            }
        });
        scoreboardBox = new VBox(scoreLabel, onlineScoreboardView);

        this.getChildren().addAll(roundBox, minigameBox, scoreboardBox);
    }

    public void update(String roundNum, String minigame) {
        // TODO fix
        this.getChildren().clear();

        roundLabel = new Label(roundNum + " / 11");
        minigameLabel= new Label(minigame) ;

        this.getChildren().addAll(roundBox, minigameBox, scoreboardBox);

    }
}
