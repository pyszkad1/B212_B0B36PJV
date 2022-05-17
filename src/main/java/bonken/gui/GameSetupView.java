package bonken.gui;

import bonken.game.Card;
import bonken.utils.Callable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameSetupView extends View {

    public GameSetupView(Callable showStartMenu, Callable startVsBotsNew, Callable startVsBotsLoad) {
        BorderPane borderPane = new BorderPane();

        Button returnBtn = new Button("RETURN");
        returnBtn.getStyleClass().add("return-button");
        returnBtn.setOnAction(event->showStartMenu.call());
        VBox returnBox = new VBox(returnBtn);
        returnBox.setAlignment(Pos.TOP_LEFT);

        Label text = new Label("Choose game mode");

        Button newGame = new Button("NEW GAME");
        newGame.getStyleClass().add("menu-button");

        Button loadGame = new Button("LOAD GAME");
        loadGame.getStyleClass().add("menu-button");

        HBox hbox = new HBox(newGame, loadGame);
        VBox menu = new VBox(text, hbox);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(28);
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(28);

        borderPane.setCenter(menu);
        borderPane.setTop(returnBox);

        Scene scene = new Scene(borderPane,1080,720);

        // TODO startVsBotsNew & startVsBotsLoad ??
        newGame.setOnAction(event->startVsBotsNew.call());
        loadGame.setOnAction(event->startVsBotsLoad.call());

        setScene(scene);
    }
}
