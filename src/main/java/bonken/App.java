package bonken;


import bonken.gui.Menu;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Menu menu = new Menu();
        menu.initMenu(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}