package bonken.gui;

import bonken.Controller;
import bonken.utils.Callable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameMenuView  extends  View {


    public GameMenuView(Callable startVsBots, Callable showStartMenu) {
        BorderPane borderPane = new BorderPane();

        Button returnBtn = new Button("RETURN");
        returnBtn.getStyleClass().add("return-button");
        returnBtn.setOnAction(event -> showStartMenu.call());
        VBox returnBox = new VBox(returnBtn);
        returnBox.setAlignment(Pos.TOP_LEFT);

        Label text = new Label("Play VS");
        Rectangle hSpace = new Rectangle(28, 28);
        Button vsBots = new Button("BOTS");
        vsBots.getStyleClass().add("menu-button");

        Rectangle vSpace = new Rectangle(28, 28);
        hSpace.setFill(Color.TRANSPARENT);
        vSpace.setFill(Color.TRANSPARENT);
        Button vsPlayers = new Button("PLAYERS");
        vsPlayers.getStyleClass().add("menu-button");

        HBox hbox = new HBox(vsBots, vSpace, vsPlayers);
        VBox menu = new VBox(text, hSpace, hbox);
        hbox.setAlignment(Pos.CENTER);
        menu.setAlignment(Pos.CENTER);

        borderPane.setCenter(menu);
        borderPane.setTop(returnBox);

        Scene scene = new Scene(borderPane, 1080, 720);

        // TODO config
        vsBots.setOnAction(event -> startVsBots.call());



        setScene(scene);

    }
}
