package bonken.gui;

import bonken.game.Game;
import bonken.utils.Callable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.nio.Buffer;

public class EndGameView extends View {

    Pane pane;
    VBox whole;

    Label gameFinished;

    VBox scoreBox;
    HBox players;
    HBox playersScore;

    Button returnToMenu;
    Button exit;

    Game game;

    public EndGameView () {

       pane  = new BorderPane();
        whole = new VBox();
        gameFinished = new Label("GAME FINISHED");
        scoreBox = new VBox();


        returnToMenu = new Button("RETURN TO MENU");
        exit = new Button("EXIT");

        whole.getChildren().addAll(gameFinished, scoreBox, returnToMenu, exit);

    }

    public void setGame(Game game) {
        this.game = game;
        players = new HBox();
        for (int i = 0; i < 4; i++) {
            players.getChildren().add(new Label(game.getPlayers()[i].getUsername()));
        }
        scoreBox.getChildren().add(players);

        playersScore = new HBox();
        for (int i = 0; i < 4; i++) {
            playersScore.getChildren().add(new Label(String.valueOf(game.getPlayers()[i].getScore())));
        }
        scoreBox.getChildren().add(playersScore);
    }

    public void show() {
        System.out.println("I WAS CALLED");
        whole.setAlignment(Pos.TOP_CENTER);
        whole.setTranslateY(50);
        whole.setTranslateX(400);
        pane.getChildren().add(whole);

        Scene scene = new Scene(pane, 1080, 720);
        setScene(scene);
    }

}
