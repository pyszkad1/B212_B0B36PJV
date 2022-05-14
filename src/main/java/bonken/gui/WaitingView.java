package bonken.gui;

import bonken.utils.Callable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class WaitingView extends View {


    public WaitingView() {
        Label label = new Label("WAITING FOR OWNER TO START GAME");

        HBox hb = new HBox(label);
        hb.setAlignment(Pos.CENTER);

        Scene scene = new Scene(hb, 1080, 720);
        setScene(scene);
    }



}
