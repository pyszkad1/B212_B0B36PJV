package bonken.gui;

import javafx.scene.Scene;

/**
 * Abstract class for all GUI scenes.
 */
public abstract class View {

    private String css = this.getClass().getResource("/bonken/gui/style.css").toExternalForm();

    private Scene scene;

    public Scene getScene() {
        return  scene;
    }

    protected void setScene(Scene scene) {

        scene.getStylesheets().add(css);
        this.scene = scene;
    }

}
