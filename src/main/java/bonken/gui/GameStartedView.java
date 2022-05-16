package bonken.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameStartedView extends View {


    public GameStartedView() {
        Label gameStarted = new Label("ONLINE GAME STARTED");
        gameStarted.getStyleClass().add("header-label");
        Label waiting = new Label("Waiting for player to choose a minigame.");

        VBox vb = new VBox(gameStarted, waiting);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(40);

        //HBox hb = new HBox(gameStarted);
        //hb.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vb, 1080, 720);
        setScene(scene);
    }



}
