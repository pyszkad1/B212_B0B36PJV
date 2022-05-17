package bonken.gui;

import bonken.game.Game;
import bonken.utils.Callable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OnlineEndGameView extends View {

    Label gameFinished;
    VBox vb;
    VBox player0;
    VBox player1;
    VBox player2;
    VBox player3;
    Label username0;
    Label username1;
    Label username2;
    Label username3;
    Label score0;
    Label score1;
    Label score2;
    Label score3;
    HBox hbButtons;
    HBox hbScore;
    Button returnButton;
    Button exitButton;

    public OnlineEndGameView(Callable showStartMenu, Callable onClose) {

        username0 = new Label();
        username1 = new Label();
        username2 = new Label();
        username3 = new Label();

        score0 = new Label();
        score1 = new Label();
        score2 = new Label();
        score3 = new Label();

        player0 = new VBox(username0, score0);
        player1 = new VBox(username1, score1);
        player2 = new VBox(username2, score2);
        player3 = new VBox(username3, score3);

        player0.setSpacing(10);
        player1.setSpacing(10);
        player2.setSpacing(10);
        player3.setSpacing(10);



        vb = new VBox();
        hbScore = new HBox();
        gameFinished = new Label("GAME FINISHED");
        gameFinished.getStyleClass().add("header-label");

        hbScore.getChildren().addAll(player0, player1, player2, player3);
        hbScore.setSpacing(40);
        hbScore.setAlignment(Pos.CENTER);

        returnButton = new Button("MENU");
        exitButton = new Button("EXIT");

        returnButton.setOnAction(event -> showStartMenu.call());    // TODO fix menu or delete menu button
        exitButton.setOnAction(event -> onClose.call());

        returnButton.getStyleClass().add("endgame-button");
        exitButton.getStyleClass().add("endgame-button");
        hbButtons = new HBox(returnButton, exitButton);
        hbButtons.setSpacing(50);
        hbButtons.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(gameFinished, hbScore, hbButtons);
        vb.setSpacing(50);
        vb.setAlignment(Pos.CENTER);
    }

    public void getScoreboard(String[] players, String[] score) {
        username0.setText(players[0]);
        username1.setText(players[1]);
        username2.setText(players[2]);
        username3.setText(players[3]);
        score0.setText(score[0]);
        score1.setText(score[1]);
        score2.setText(score[2]);
        score3.setText(score[3]);
    }

    public void show() {
        Scene scene = new Scene(vb, 1080, 720);
        setScene(scene);
    }

}
