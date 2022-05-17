package bonken.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.logging.Logger;

/**
 * Main GUI class, combines CardPane at the bottom, TrickPane and MinigameChoicePane in the middle and StatusPane in the top left corner.
 */
public class GameView extends View{

    private static final Logger LOGGER = Logger.getLogger(GameView.class.getName());
    private CardPane cardPane;
    private MinigameChoicePane minigameChoicePane;
    private OnlineTrickPane onlineTrickPane;
    private OfflineTrickPane offlineTrickPane;
    private StackPane centerPane;
    private BorderPane borderPane;
    private Rectangle blockingRec;
    private StackPane wholeScreen;

    public void showMinigameChoice() {
        minigameChoicePane.setVisible(true);
        if (onlineTrickPane == null) {
            offlineTrickPane.setVisible(false);
        } else {
            onlineTrickPane.setVisible(false);
        }
    }

    public void hideMinigameChoice() {
        minigameChoicePane.setVisible(false);
        if (onlineTrickPane == null) {
            offlineTrickPane.setVisible(true);
        } else {
            onlineTrickPane.setVisible(true);
        }
    }

    /**
     * GameView constructor for offline game.
     * @param minigamePane
     * @param cardPane
     * @param offlineTrickPane
     */
    public GameView( MinigameChoicePane minigamePane, CardPane cardPane, OfflineTrickPane offlineTrickPane) {

        LOGGER.info("Started Game View ------- OFFLINE");

        this.cardPane = cardPane;
        cardPane.setMaxWidth(1080);
        cardPane.setAlignment(Pos.BOTTOM_CENTER);
        cardPane.setPrefHeight(150);
        cardPane.getStyleClass().add("card-box");

        this.minigameChoicePane = minigamePane;
        minigamePane.setPrefHeight(500);

        this.offlineTrickPane = offlineTrickPane;

        centerPane = new StackPane();
        centerPane.getChildren().add(offlineTrickPane);
        centerPane.getChildren().add(minigamePane);

        borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        borderPane.setBottom(cardPane);
        borderPane.setMaxWidth(1080);

        blockingRec = new Rectangle(1080, 200);

        blockingRec.setFill(Color.TRANSPARENT);

        wholeScreen = new StackPane();
        wholeScreen.setAlignment(blockingRec, Pos.BOTTOM_CENTER) ;
        wholeScreen.getChildren().addAll(blockingRec, borderPane);

        hideMinigameChoice();

        Scene scene = new Scene(wholeScreen, 1080, 720);
        setScene(scene);
    }

    /**
     * GameView constructor for net game.
     * @param minigamePane
     * @param cardPane
     * @param onlineTrickPane
     */
    public GameView( MinigameChoicePane minigamePane, CardPane cardPane, OnlineTrickPane onlineTrickPane) {

        LOGGER.info("Started Game View ------- ONLINE");

        this.cardPane = cardPane;
        cardPane.setMaxWidth(1080);
        cardPane.setAlignment(Pos.BOTTOM_CENTER);
        cardPane.setPrefHeight(150);
        cardPane.getStyleClass().add("card-box");

        this.minigameChoicePane = minigamePane;
        minigamePane.setPrefHeight(500);

        this.onlineTrickPane = onlineTrickPane;

        centerPane = new StackPane();
        centerPane.getChildren().add(onlineTrickPane);
        centerPane.getChildren().add(minigamePane);

        borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        borderPane.setBottom(cardPane);
        borderPane.setMaxWidth(1080);

        blockingRec = new Rectangle(1080, 200);

        blockingRec.setFill(Color.TRANSPARENT);

        wholeScreen = new StackPane();
        wholeScreen.setAlignment(blockingRec, Pos.BOTTOM_CENTER) ;
        wholeScreen.getChildren().addAll(blockingRec, borderPane);

        hideMinigameChoice();

        Scene scene = new Scene(wholeScreen, 1080, 720);
        setScene(scene);
    }

    /**
     * Blocks cardPane.
     */
    public void showBlockingRec() {
        blockingRec.toFront();
    }

    /**
     * Enables cardPane.
     */
    public void hideBlockingRec(){
        borderPane.toFront();
    }


}
