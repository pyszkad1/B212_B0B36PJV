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

/**
 * Class that shows username input screen.
 */
public class NameInputView extends  View {

    private Action<String> onNameInput;
    private TextField textField;
    private Label label, label1;
    private BorderPane borderPane;
    private Button submitButton;
    private VBox vb;

    /**
     * Asks for username.
     * @param onNameInput sends info to model (or server)
     */
    public NameInputView(Action<String> onNameInput) {

        this.onNameInput = onNameInput;

        label1 = new Label("Enter your username:");

        textField = new TextField();
        textField.setMaxWidth(400);

        submitButton = new Button("OK");
        submitButton.getStyleClass().add("ok-button");
        submitButton.setOnAction(e -> trySubmit());

        label = new Label();
        label.getStyleClass().add("small-label");

        vb = new VBox();
        vb.getChildren().addAll(label1, textField, submitButton, label);
        vb.setSpacing(30);
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
        if ((textField.getText() != null && (textField.getText().length() > 0 && textField.getText().length() <= 8))) {
            onNameInput.call(textField.getText());
        } else if ((textField.getText().length() > 8)) {
            label.setText("Username is too long.");
        } else {
            label.setText("Please enter a valid username.");
        }

    }
}
