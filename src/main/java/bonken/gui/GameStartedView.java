package bonken.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class GameStartedView extends View {


    public GameStartedView() {
        Label label = new Label("ONLINE GAME STARTED");
        label.getStyleClass().add("header-label");
        //TODO waiting for player to choose minigame

        HBox hb = new HBox(label);
        hb.setAlignment(Pos.CENTER);

        Scene scene = new Scene(hb, 1080, 720);
        setScene(scene);
    }



}
