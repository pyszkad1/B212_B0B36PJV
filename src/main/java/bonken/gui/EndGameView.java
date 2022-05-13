package bonken.gui;

import bonken.utils.Callable;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

public class EndGameView extends View {

    BorderPane borderPane;

    public EndGameView () {
        borderPane  = new BorderPane();

    }

    public void show() {
        System.out.println("I WAS CALLED");

        Scene scene = new Scene(borderPane, 1080, 720);
        setScene(scene);
    }

}
