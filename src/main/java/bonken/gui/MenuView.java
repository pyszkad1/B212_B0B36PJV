package bonken.gui;

import bonken.game.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuView {

    String css = this.getClass().getResource("/bonken/gui/menu_style.css").toExternalForm();

    public void initMenu(Stage stage) {

        Font nameFont = Font.loadFont(getClass().getResourceAsStream("/bonken/gui/fonts/8-bit_Arcade_Out.ttf"), 300);
        Font btnFont = Font.loadFont(getClass().getResourceAsStream("/bonken/gui/fonts/Fleftex_M.ttf"), 30);

        Label name = new Label("BONKEN");
        Button startBtn = new Button("start");
        Button exitBtn = new Button("exit");

        //name.setFont(nameFont);
        //startBtn.setFont(btnFont);
        //exitBtn.setFont(btnFont);

        VBox menuButtons = new VBox(20, startBtn, exitBtn);
        menuButtons.setPadding(new Insets(5));
        menuButtons.setAlignment(Pos.CENTER);

        VBox menu = new VBox(name, menuButtons);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);

        //menu.setTranslateX(400);
        //menu.setTranslateY(400);

        Scene scene = new Scene(menu, 1920, 1080);

        startBtn.setOnAction(event -> initStartGame(stage));
        exitBtn.setOnAction(event -> stage.close());

        scene.getStylesheets().add(css);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void initStartGame(Stage stage) {

        Label text = new Label("Play VS");

        Button vsBots = new Button("BOTS");
        Rectangle rectangle = new Rectangle(28, 28);
        rectangle.setFill(Color.TRANSPARENT);
        Button vsPlayers = new Button("PLAYERS");
        HBox hbox = new HBox(vsBots, rectangle, vsPlayers);
        VBox menu = new VBox(text, hbox);
        hbox.setAlignment(Pos.CENTER);
        menu.setAlignment(Pos.CENTER);


        Scene scene = new Scene(menu, 1920, 1080);

        //vsBots.setOnAction(event -> game.startGame());


        scene.getStylesheets().add(css);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }
}
