package bonken.gui;

import bonken.game.Card;
import bonken.game.Game;
import bonken.game.PlayerInterface;
import bonken.utils.Action;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameView extends View{


    private Game game;

    private CardPane cardPane;
    private BorderPane borderPane;
    private MinigameChoicePane minigameChoicePane;
    private TrickPane trickPane;

    private StackPane centerPane;

    public void showMinigameChoice() {
        minigameChoicePane.setVisible(true);
        trickPane.setVisible(false);
    }

    public void hideMinigameChoice() {
        minigameChoicePane.setVisible(false);
        trickPane.setVisible(true);
    }

    public GameView( MinigameChoicePane minigamePane, CardPane cardPane, TrickPane trickPane) {



        System.out.println("Starting Game!! --------- :)");

        this.cardPane = cardPane;
        cardPane.setMaxWidth(1080);
        cardPane.setAlignment(Pos.BOTTOM_CENTER);
        cardPane.setPrefHeight(150);
        cardPane.getStyleClass().add("card-box");

        this.minigameChoicePane = minigamePane;
        minigamePane.setPrefHeight(500);

        this.trickPane = trickPane;

        centerPane = new StackPane();
        centerPane.getChildren().add(trickPane);
        centerPane.getChildren().add(minigamePane);

        borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        borderPane.setBottom(cardPane);
        borderPane.setMaxWidth(1080);

        showMinigameChoice();


        Scene scene = new Scene(borderPane, 1080, 720);
        setScene(scene);
    }

    public void setGame(Game game) {
        this.game = game;

        PlayerInterface player = game.getPlayers()[0];

        cardPane.setPlayer(player);

    }

}
