package bonken;


import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        stage.setResizable(false);
        Controller controller = new Controller(stage);
        stage.setOnCloseRequest(event -> {stage.close(); controller.close();});
        controller.start();
    }

    public static void main(String[] args) {
        launch();
    }

}