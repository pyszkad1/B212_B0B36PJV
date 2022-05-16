package bonken.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameStartedView extends View {


    public GameStartedView() {
        Label gameStarted = new Label("ONLINE GAME STARTED");
        Label waiting = new Label("Waiting for player to choose a minigame.");
        waiting.getStyleClass().add("small-label");

        VBox vb = new VBox(gameStarted, waiting);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(40);

        Scene scene = new Scene(vb, 1080, 720);
        setScene(scene);
    }



}
