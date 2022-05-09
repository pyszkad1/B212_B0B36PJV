package bonken.gui;

import bonken.game.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class OfflineGameView {
    Game game;
    String css = this.getClass().getResource("/bonken/gui/menu_style.css").toExternalForm();

    public void initGameView(Stage stage) {
        game = new Game(1);
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
                    game.getPlayersOffline(textField.getText());
                    miniGameChoiceView(stage, 0);

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

    public void miniGameChoiceView(Stage stage, int roundNum) {
        if (roundNum == 0){
            game.startGameOffline();
        }
        if (roundNum >= 11) {
            //TODO
            //END OF GAME
        }
        BorderPane borderPane = new BorderPane();
        game.startRoundOffline();

        HBox mgHbox = new HBox();
        for (int i = 0; i < game.getMinigames().size(); i++) {

        }

        roundNum++;
        miniGameChoiceView(stage, roundNum);

    }

    public void render() {

    }
}
