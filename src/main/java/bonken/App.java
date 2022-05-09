package bonken;


import bonken.gui.MenuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        MenuView menu = new MenuView();
        menu.initMenu(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}