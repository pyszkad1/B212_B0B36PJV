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


    public GameMenuView(Callable chooseVsBots, Callable showStartMenu, Callable startVsPlayers) {
        BorderPane borderPane = new BorderPane();

        Button returnBtn = new Button("RETURN");
        returnBtn.getStyleClass().add("return-button");
        returnBtn.setOnAction(event -> showStartMenu.call());
        VBox returnBox = new VBox(returnBtn);
        returnBox.setAlignment(Pos.TOP_LEFT);

        Label text = new Label("Play against");

        Button vsBots = new Button("BOTS");
        vsBots.getStyleClass().add("menu-button");

        Button vsPlayers = new Button("PLAYERS");
        vsPlayers.getStyleClass().add("menu-button");

        HBox hbox = new HBox(vsBots, vsPlayers);
        VBox menu = new VBox(text, hbox);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(28);
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(28);

        borderPane.setCenter(menu);
        borderPane.setTop(returnBox);

        Scene scene = new Scene(borderPane, 1080, 720);

        vsBots.setOnAction(event -> chooseVsBots.call());
        vsPlayers.setOnAction(event -> startVsPlayers.call());



        setScene(scene);

    }
}
