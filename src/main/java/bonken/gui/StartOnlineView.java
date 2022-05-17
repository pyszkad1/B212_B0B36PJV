package bonken.gui;

import bonken.utils.Callable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Class for showing Start game screen to hosting player.
 */
public class StartOnlineView extends View {
    /**
     *
     * @param onOnlineStart calls online game start on button clicked
     */
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
