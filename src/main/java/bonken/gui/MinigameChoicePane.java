package bonken.gui;

import bonken.game.Minigames;
import bonken.utils.Action;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Class for showing minigames choices to player.
 */
public class MinigameChoicePane extends FlowPane {

    ArrayList<Button> minigameButtons;
    private ArrayList<Integer> availableMinigames;

    /**
     *
     * @param onMinigameChosen call on button click
     */
    public MinigameChoicePane(Action<Minigames> onMinigameChosen) {
        super();

        Rectangle space = new Rectangle(20, 20);
        space.setFill(Color.TRANSPARENT);

        this.setAlignment(Pos.CENTER);

        minigameButtons = new ArrayList<>();
        availableMinigames = new ArrayList<>();

        for(Minigames minigame : Minigames.values()) {
            String str = minigame.name;
            Button button = new Button(str);

            button.getStyleClass().add("minigame-choice-button");
            int finalI = minigame.num;
            button.setOnAction(event -> {
                if(availableMinigames.contains(minigame.num))
                    onMinigameChosen.call(minigame);
            });
            this.getChildren().add(button);
            minigameButtons.add(button);
        }
    }

    /**
     * Disables buttons for non available minigames.
     * @param availableMinigames
     */
    public void setAvailableMinigames(ArrayList<Integer> availableMinigames) {

        this.availableMinigames = availableMinigames;

        for (Integer i = 0; i < minigameButtons.size(); i++) {

            if(availableMinigames.contains(i)) {
                minigameButtons.get(i).getStyleClass().removeIf(string -> string.equals( "minigamechoice-button-inactive"));
                minigameButtons.get(i).getStyleClass().add("minigamechoice-button-active");
            }
            else {
                minigameButtons.get(i).getStyleClass().removeIf(string -> string.equals( "minigamechoice-button-active"));
                minigameButtons.get(i).getStyleClass().add("minigamechoice-button-inactive");
            }

        }
    }

}
