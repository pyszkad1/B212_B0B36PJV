package bonken.gui;

import bonken.utils.Action;
import bonken.utils.Callable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NameInputView extends  View {

    private Action<String> onNameInput;
    private TextField textField;
    private Label label, label1;
    private BorderPane borderPane;
    private Button submitButton;
    private VBox vb;

    public NameInputView(Action<String> onNameInput) {

        this.onNameInput = onNameInput;

        label1 = new Label("Choose your username:");

        textField = new TextField();

        submitButton = new Button("ok");
        submitButton.getStyleClass().add("menu-button");
        submitButton.setOnAction(e -> trySubmit());

        label = new Label();

        vb = new VBox();
        vb.getChildren().addAll(label1, textField, submitButton, label);
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);

        borderPane = new BorderPane();
        borderPane.setCenter(vb);

        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) trySubmit();
        });

        setScene(new Scene(borderPane, 1080, 720));
    }

    private void trySubmit()
    {
        if ((textField.getText() != null && !textField.getText().isEmpty())) {
            onNameInput.call(textField.getText());

        } else {
            label.setText("You have not left a comment.");
        }
    }
}
