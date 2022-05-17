package bonken.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class for showing statusPane in net game.
 */
public class OnlineStatusPane extends VBox {

    public Label minigameLabel , roundLabel, roundHeader, scoreLabel;
    public HBox minigameBox, roundBox;
    public VBox scoreboardBox;
    public OnlineScoreboardView onlineScoreboardView;

    /**
     * Shows game status (round and current minigame).
     * @param onlineScoreboardView
     */
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

    /**
     * Updates number of round and minigame upon receiving them from server.
     * @param roundNum
     * @param minigame
     */
    public void update(String roundNum, String minigame) {
        roundLabel.setText(roundNum + " / 11");
        minigameLabel.setText(minigame);


    }
}
