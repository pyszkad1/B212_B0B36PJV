package bonken;

import bonken.game.Position;
import bonken.gui.*;
import bonken.net.Client;
import bonken.net.Protocol;
import bonken.utils.Callable;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Class for GUI and Client interactions.
 */
public class OnlineController {

    private Position myPosition;
    public Stage stage;
    public StartMenuView startMenuView;

    private String username;
    private MinigameChoicePane minigameChoicePane;
    private CardPane cardPane;
    private OnlineTrickPane trickPane;
    private GameView gameView;
    private OnlineEndGameView onlineEndGameView;
    private GameStartedView gameStartedView;
    private OnlineStatusPane onlineStatusPane;
    private OnlineScoreboardView onlineScoreboardView;

    private Controller controller;
    private Client client;
    private Callable onMinigameRequired;

    /**
     *
     * @param stage
     * @param client
     * @param startMenuView
     * @param controller main game controller
     */
    public OnlineController(Stage stage, Client client, StartMenuView startMenuView, Controller controller) {
        this.client = client;
        this.stage = stage;
        this.startMenuView = startMenuView;
        this.controller = controller;

        this.minigameChoicePane = new MinigameChoicePane( minigame -> {
            gameView.hideMinigameChoice();
            client.sendToServer(Protocol.MINIGAME,  String.valueOf(minigame.num));
        });
        this.cardPane = new CardPane(card -> {client.sendToServer(Protocol.CARD, card); cardPane.updateAfter(currentStringCardHand); trickPane.packUpTrick();  });
    }

    /**
     * Get position from server.
     * @param pos
     */
    public void setMyPos(Position pos){
        this.myPosition = pos;
        this.onlineScoreboardView = new OnlineScoreboardView();

        this.onlineStatusPane = new OnlineStatusPane(onlineScoreboardView);
        this.trickPane = new OnlineTrickPane(myPosition, () -> gameView.showBlockingRec(), () -> gameView.hideBlockingRec(), onlineStatusPane);
        this.onlineEndGameView = new OnlineEndGameView(() -> { stage.setScene(startMenuView.getScene()); client.close();}, () -> {stage.close(); this.close();});
        gameView = new GameView(minigameChoicePane, cardPane, trickPane);
    }

    public Position getMyPosition() {
        return myPosition;
    }

    private String[] currentStringCardHand;

    public void setCurrentStringCardHand(String[] currentStringCardHand) {
        this.currentStringCardHand = currentStringCardHand;
    }

    private boolean showingGameView = false;

    /**
     * Updates GUI.
     * @param stringFirstPlayer
     * @param trick
     * @param cardHand
     * @param playableCards
     */
    public void updateGui(String stringFirstPlayer, String[] trick, String[] cardHand, String[] playableCards){
        int firstPlayer = Integer.valueOf(stringFirstPlayer);
        trickPane.update(firstPlayer, trick);
        Platform.runLater(() -> cardPane.updateBefore(cardHand, playableCards));
    }

    /**
     * Updates card hand after playing trick.
     * @param cardHand
     */
    public void showCardHand(String[] cardHand) {
        Platform.runLater(() -> cardPane.updateAfter(cardHand));
    }

    /**
     * Gets data from server and updates GUI on trick end.
     * @param firstPlayerAgain
     * @param wholeTrick
     */
    public void updateTrickEnd(String firstPlayerAgain, String[] wholeTrick){
        int firstPlayer = Integer.valueOf(firstPlayerAgain);
        trickPane.updateOnTrickEnd(firstPlayer, wholeTrick);

    }

    /**
     * Sets stage to show main game window.
     */
    public void showGameView() {
        if (!showingGameView){
        stage.setScene(gameView.getScene());
        stage.show();
        showingGameView = true;
        }
    }

    /**
     * Sets stage to show game started screen.
     */
    public void showGameStarted() {
        gameStartedView = new GameStartedView();
        Platform.runLater(() -> stage.setScene(gameStartedView.getScene()));
    }

    public void setPossibleMinigames(ArrayList<Integer> minigames) {
        minigameChoicePane.setAvailableMinigames(minigames);
    }

    /**
     * Sets stage to show minigame choice.
     */
    public void showMiniGameChoiceView() {
        gameView.showMinigameChoice();
    }

    /**
     * Sets stage to show ending screen.
     */
    public void showEndGameScreen() {
        onlineEndGameView.show();
        stage.setScene(onlineEndGameView.getScene());
    }

    public void close() {
        if (trickPane != null) trickPane.killTimer();
        if (client != null) client.close();
    }

    /**
     * Calls online status update with data from server.
     * @param roundNum
     * @param minigame
     */
    public void updateStatus(String roundNum, String minigame) {
        onlineStatusPane.update(roundNum, minigame);
    }

    /**
     * Calls online scoreboard update with data from server.
     * @param players
     * @param score
     */
    public void updateScoreboard(String[] players, String[] score) {
        onlineScoreboardView.update(players, score);
        onlineEndGameView.getScoreboard(players, score);
    }
}
