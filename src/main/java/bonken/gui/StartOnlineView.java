package bonken.gui;

import bonken.game.Game;
import bonken.utils.Callable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class StartOnlineView extends View {

    public StartOnlineView(Callable onOnlineStart) {
        Button startButton = new Button("START GAME");
        startButton.getStyleClass().add("menu-button");
        startButton.setOnAction(event -> onOnlineStart.call());

        HBox hb = new HBox(startButton);
        hb.setAlignment(Pos.CENTER);

        Scene scene = new Scene(hb, 1080, 720);
        setScene(scene);
    }


}
