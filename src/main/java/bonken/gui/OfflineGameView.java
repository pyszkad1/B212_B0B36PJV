package bonken.gui;

import bonken.game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class OfflineGameView {
    Game game;
    String css = this.getClass().getResource("/bonken/gui/menu_style.css").toExternalForm();

    public void initGameView(Game game, Stage stage) {
        BorderPane borderPane = new BorderPane();
        Label label1 = new Label("Name:");
        TextField textField = new TextField();
        Button submitButton = new Button("Submit");
        Label label = new Label();
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, submitButton, label);
        hb.setSpacing(10);
        borderPane.setCenter(hb);

        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if ((textField.getText() != null && !textField.getText().isEmpty())) {
                    game.fillPlayersArrayOffline(textField.getText());

                } else {
                    label.setText("You have not left a comment.");
                }
            }
        });

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public int miniGameChoiceView(Stage stage, ArrayList<Integer> minigames) {
        int ret;

        BorderPane borderPane = new BorderPane();
        game.startRoundOffline();

        HBox minigameBox = new HBox();
        Rectangle space = new Rectangle(20, 20);
        space.setFill(Color.TRANSPARENT);

        for (int i = 0; i < 12; i++) {
            String str = String.valueOf(i);
            Button button = new Button(str);
            button.setOnAction(event -> );
            minigameBox.getChildren().add(button);
            if (i != 11) {
                minigameBox.getChildren().add(space);
            }
        }

        minigameBox.setAlignment(Pos.CENTER);

        borderPane.setCenter(minigameBox);


        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();

        return ret;
    }

    public void render() {

    }
}
