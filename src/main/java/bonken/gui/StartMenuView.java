package bonken.gui;

import bonken.utils.Callable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class StartMenuView extends View {

    private Button exitBtn, startBtn;
    private Label mainLabel;
    private VBox menuButtons, menu;

    public StartMenuView(Callable onStart, Callable onClose) {

        mainLabel = new Label("BONKEN");

        startBtn = new Button("START");
        startBtn.getStyleClass().add("menu-button");
        startBtn.setOnAction(event -> onStart.call());

        exitBtn = new Button("EXIT");
        exitBtn.getStyleClass().add("menu-button");
        exitBtn.setOnAction(event -> onClose.call());

        menuButtons = new VBox(20, startBtn, exitBtn);
        menuButtons.setPadding(new Insets(5));
        menuButtons.setAlignment(Pos.CENTER);

        menu = new VBox(mainLabel, menuButtons);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);

        this.setScene(new Scene(menu, 1080, 720));
    }

}
