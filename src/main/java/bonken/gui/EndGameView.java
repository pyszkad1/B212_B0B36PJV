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

    Label gameFinished;
    VBox vb;
    VBox player0;
    VBox player1;
    VBox player2;
    VBox player3;
    Label playerScore0;
    Label playerScore1;
    Label playerScore2;
    Label playerScore3;
    HBox hbButtons;
    HBox hbScore;
    Button returnButton;
    Button exitButton;

    Game game;

    public EndGameView (Callable showStartMenu, Callable onClose) {
        vb = new VBox();
        hbScore = new HBox();
        gameFinished = new Label("GAME FINISHED");
        gameFinished.getStyleClass().add("header-label");

        returnButton = new Button("MENU");
        exitButton = new Button("EXIT");

        returnButton.setOnAction(event -> showStartMenu.call());
        exitButton.setOnAction(event -> onClose.call());

        returnButton.getStyleClass().add("endgame-button");
        exitButton.getStyleClass().add("endgame-button");
        hbButtons = new HBox(returnButton, exitButton);
        hbButtons.setSpacing(50);
        hbButtons.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(gameFinished, hbScore, hbButtons);
        vb.setSpacing(50);

    }

    public void setGame(Game game) {
        this.game = game;

        playerScore0 = new Label();
        playerScore1 = new Label();
        playerScore2 = new Label();
        playerScore3 = new Label();

        player0 = new VBox(new Label(game.getPlayers()[0].getUsername()), playerScore0);
        player1 = new VBox(new Label(game.getPlayers()[1].getUsername()), playerScore1);
        player2 = new VBox(new Label(game.getPlayers()[2].getUsername()), playerScore2);
        player3 = new VBox(new Label(game.getPlayers()[3].getUsername()), playerScore3);
        player0.setSpacing(10);
        player1.setSpacing(10);
        player2.setSpacing(10);
        player3.setSpacing(10);

        hbScore.getChildren().addAll(player0, player1, player2, player3);
        hbScore.setSpacing(40);
        hbScore.setAlignment(Pos.CENTER);

    }

    public void show() {

        playerScore0.setText(String.valueOf( game.getPlayers()[0].getScore()));
        playerScore1.setText(String.valueOf( game.getPlayers()[1].getScore()));
        playerScore2.setText(String.valueOf( game.getPlayers()[2].getScore()));
        playerScore3.setText(String.valueOf( game.getPlayers()[3].getScore()));

        vb.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vb, 1080, 720);
        setScene(scene);
    }

}
