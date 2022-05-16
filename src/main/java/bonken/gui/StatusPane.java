package bonken.gui;

import bonken.game.Game;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import bonken.game.Minigames;

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

    private Label minigameLabel, minigameHeader , roundLabel, roundHeader, scoreLabel;
    private HBox minigameBox, roundBox;
    private VBox scoreboardBox;
    private ScoreboardView scoreboard;

    public void setGame(Game game) {
        this.game = game;
        scoreboard.setGame(game);
    }

    public StatusPane() {
        super();


        roundHeader= new Label("Round ");
        roundLabel= new Label();
        roundBox = new HBox(roundHeader, roundLabel);

        minigameLabel= new Label() ;
        minigameBox = new HBox(minigameLabel);

        scoreLabel = new Label("Score");
        scoreboard = new ScoreboardView();
        scoreLabel.hoverProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal) {
                scoreboard.show();

            } else {
                scoreboard.hide();
            }
        });
        scoreboardBox = new VBox(scoreLabel, scoreboard);

        this.getChildren().addAll(roundBox, minigameBox, scoreboardBox);

    }

    public void update() {
        int gameCounter = game.getGameCounter() + 2;        // TODO +1 ??
        if (gameCounter > 11){
            gameCounter = 11;
        }
        System.out.println(gameCounter);

        roundLabel.setText(gameCounter + " / 11");
        minigameLabel.setText(String.valueOf(Minigames.values()[game.getCurrentRound().getChosenMiniGameNum()].name));
    }
}
