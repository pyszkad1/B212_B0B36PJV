package bonken.gui;

import javafx.scene.Scene;

public abstract class View {

    private String css = this.getClass().getResource("/bonken/gui/menu_style.css").toExternalForm();

    private Scene scene;

    public Scene getScene() {
        return  scene;
    }

    protected void setScene(Scene scene) {

        scene.getStylesheets().add(css);
        this.scene = scene;
    }

}
