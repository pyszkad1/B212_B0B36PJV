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
    HBox hb;

    Button returnButton;
    Button exitButton;

    Game game;

    public EndGameView (Callable showStartMenu, Callable onClose) {

        pane  = new BorderPane();
        whole = new VBox();
        gameFinished = new Label("G A M E   F I N I S H E D");
        scoreBox = new VBox();


        returnButton = new Button("MENU");
        exitButton = new Button("EXIT");

        returnButton.setOnAction(event -> showStartMenu.call());
        exitButton.setOnAction(event -> onClose.call());


        returnButton.getStyleClass().add("endgame-button");
        exitButton.getStyleClass().add("endgame-button");
        hb = new HBox(returnButton, exitButton);
        hb.setSpacing(50);

        whole.getChildren().addAll(gameFinished, scoreBox, hb);

    }

    public void setGame(Game game) {
        this.game = game;
        players = new HBox();
        for (int i = 0; i < 4; i++) {
            players.getChildren().add(new Label(game.getPlayers()[i].getUsername()));
        }
        players.setSpacing(100);
        scoreBox.getChildren().add(players);

    }

    public void show() {

        playersScore = new HBox();
        for (int i = 0; i < 4; i++) {
            playersScore.getChildren().add(new Label(String.valueOf(game.getPlayers()[i].getScore())));
        }
        playersScore.setSpacing(100);
        scoreBox.getChildren().add(playersScore);

        System.out.println("I WAS CALLED");
        whole.setAlignment(Pos.CENTER);
        //whole.setTranslateY(50);
        //whole.setTranslateX(400);
        pane.getChildren().add(whole);

        Scene scene = new Scene(pane, 1080, 720);
        setScene(scene);
    }

}
