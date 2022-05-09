package bonken.gui;

import bonken.game.Game;
import bonken.game.Player;
import bonken.game.PlayerInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class OfflineGameView {
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
                    System.out.println("filling players");
                    game.fillPlayersArrayOffline(textField.getText());
                    game.startGameOffline();
                    miniGameChoiceView(stage, game, game.getMinigames());

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

    public void miniGameChoiceView(Stage stage, Game game, ArrayList<Integer> minigames) {

        game.startRoundOffline();

        BorderPane borderPane = new BorderPane();

        HBox minigameBox = new HBox();
        Rectangle space = new Rectangle(20, 20);
        space.setFill(Color.TRANSPARENT);

        for (int i = 0; i < 12; i++) {
            //TODO
            String str = String.valueOf(i);
            Button button = new Button(str);
            int finalI = i;
            button.setOnAction(event -> fire(finalI, game, stage));
            minigameBox.getChildren().add(button);

        }

        minigameBox.setAlignment(Pos.CENTER);

        borderPane.setCenter(minigameBox);


        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();

    }

    public void fire(int i, Game game, Stage stage) {
        game.round.setMinigameNum(i);
        game.round.chooseGame();
        roundView(stage, game);
    }

    public void roundView(Stage stage, Game game){
        System.out.println("Starting Game!! --------- :)");

        BorderPane borderPane = new BorderPane();
        HBox cardBox = new HBox();
        PlayerInterface player = game.getPlayers()[0];

        for (int i = 0; i < 13; i++) {
            System.out.println("/bonken/gui/cards/" + player.getCardHand().getHand().get(i).getImage());
            String image = this.getClass().getResource("/bonken/gui/cards/" + player.getCardHand().getHand().get(i).getImage()).toExternalForm();
            ImageView imageView = new ImageView(new Image(image));
            cardBox.getChildren().add(imageView);
        }

        cardBox.setAlignment(Pos.BOTTOM_CENTER);

        borderPane.setCenter(cardBox);


        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();


        game.round.playRound();
    }

    public void render() {

    }
}
