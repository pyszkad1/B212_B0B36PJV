package bonken.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Menu {

    public void initMenu(Stage stage) {
        Rectangle background = new Rectangle(1920, 1080);
        background.setFill(Color.WHITESMOKE);

        Font nameFont = Font.loadFont(getClass().getResourceAsStream("/bonken/gui/fonts/8-bit_Arcade_Out.ttf"), 300);
        Font btnFont = Font.loadFont(getClass().getResourceAsStream("/bonken/gui/fonts/Fleftex_M.ttf"), 30);

        Label name = new Label("BONKEN");
        Button startBtn = new Button("s t a r t");
        Button exitBtn = new Button("e x i t");

        //name.setFont(nameFont);
        //startBtn.setFont(btnFont);
        //exitBtn.setFont(btnFont);
        exitBtn.setOnAction(event -> stage.close());

        VBox menuButtons = new VBox(20, startBtn, exitBtn);
        menuButtons.setPadding(new Insets(5));

        VBox menu = new VBox(name, menuButtons);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);
        //menu.setTranslateX(400);
        //menu.setTranslateY(400);

        Pane mainMenu = new Pane();
        mainMenu.getChildren().addAll(background, menu);

        Scene scene = new Scene(mainMenu);
        //scene.getStylesheets().add("bonken/gui/menu_style.css");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
