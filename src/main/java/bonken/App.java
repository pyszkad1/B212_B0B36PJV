package bonken;


import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        Controller controller = new Controller(stage);
        controller.start();
    }

    public static void main(String[] args) {
        launch();
    }

}