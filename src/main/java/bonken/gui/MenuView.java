package bonken.gui;

import bonken.game.Game;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuView {

    String css = this.getClass().getResource("/bonken/gui/menu_style.css").toExternalForm();
    GameView gameView;

    public void initMenu(Stage stage) {

        Label name = new Label("BONKEN");
        Button startBtn = new Button("START");
        Button exitBtn = new Button("EXIT");

        VBox menuButtons = new VBox(20, startBtn, exitBtn);
        menuButtons.setPadding(new Insets(5));
        menuButtons.setAlignment(Pos.CENTER);

        VBox menu = new VBox(name, menuButtons);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);

        //menu.setTranslateX(400);
        //menu.setTranslateY(400);

        Scene scene = new Scene(menu);

        startBtn.setOnAction(event -> initStartGameMenu(stage));
        exitBtn.setOnAction(event -> stage.close());

        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void initStartGameMenu(Stage stage) {
        gameView = new GameView();
        BorderPane borderPane = new BorderPane();

        Button returnBtn = new Button("RETURN");
        returnBtn.setOnAction(event -> initMenu(stage));
        VBox returnBox = new VBox(returnBtn);
        returnBox.setAlignment(Pos.TOP_LEFT);

        Label text = new Label("Play VS");
        Rectangle hSpace = new Rectangle(28, 28);
        Button vsBots = new Button("BOTS");
        Rectangle vSpace = new Rectangle(28, 28);
        hSpace.setFill(Color.TRANSPARENT);
        vSpace.setFill(Color.TRANSPARENT);
        Button vsPlayers = new Button("PLAYERS");
        HBox hbox = new HBox(vsBots, vSpace, vsPlayers);
        VBox menu = new VBox(text, hSpace, hbox);
        hbox.setAlignment(Pos.CENTER);
        menu.setAlignment(Pos.CENTER);

        borderPane.setCenter(menu);
        borderPane.setTop(returnBox);


        Scene scene = new Scene(borderPane);

        // TODO config
        vsBots.setOnAction(event -> gameView.initGameView(stage));

        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();

    }
}
