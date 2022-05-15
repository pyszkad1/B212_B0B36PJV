package bonken;

import bonken.game.*;
import bonken.gui.*;
import bonken.net.Client;
import bonken.net.Protocol;
import bonken.utils.Callable;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OnlineController {

    private Position position;
    public Stage stage;

    public StartMenuView startMenuView;

    private String username;
    private MinigameChoicePane minigameChoicePane;
    private CardPane cardPane;
    private TrickPane trickPane;
    private GameView gameView;
    private EndGameView endGameView;
    private GameStartedView gameStartedView;

    private Client client;
    private Callable onMinigameRequired;

    public OnlineController(Stage stage, Client client, StartMenuView startMenuView, Position position) {
        this.client = client;
        this.stage = stage;
        this.position = position;
        this.startMenuView = startMenuView;

        this.minigameChoicePane = new MinigameChoicePane( minigame -> {
            gameView.hideMinigameChoice();
            client.sendToServer(Protocol.MINIGAME,  String.valueOf(minigame.num));
        });
        this.cardPane = new CardPane(card -> {client.sendToServer(Protocol.CARD, card); cardPane.updateAfter(currentStringCardHand); trickPane.packUpTrick();  });
        this.trickPane = new TrickPane(position, () -> gameView.showBlockingRec(), () -> gameView.hideBlockingRec());
        this.endGameView = new EndGameView(() -> { stage.setScene(startMenuView.getScene());}, () -> {stage.close(); this.close();});
        gameView = new GameView(minigameChoicePane, cardPane, trickPane);
    }

    private String[] currentStringCardHand;

    public void setCurrentStringCardHand(String[] currentStringCardHand) {
        this.currentStringCardHand = currentStringCardHand;
    }

    public void updateHand(String card) {
        ArrayList<String> handList = new ArrayList<>();
        for (String string : currentStringCardHand){
            handList.add(string);
        }

        for (String string: handList) {
            if (string.equals(card)){
                handList.remove(card);
            }
        }
        for (int i = 0; i < handList.size(); i++) {
            currentStringCardHand[i] = handList.get(i);
        }
    }

    private boolean showingGameView = false;
    public void updateGui(String[] trick, String[] cardHand, String[] playableCards){
        //trickPane.update(trick);

        Platform.runLater(() -> cardPane.updateBefore(cardHand, playableCards));

    }

    public void start() {
        stage.setScene(startMenuView.getScene());
        stage.show();
    }

    public void showGameView() {
        if (!showingGameView){
        stage.setScene(gameView.getScene());
        stage.show();
        showingGameView = true;
        }
    }

    public void showGameStarted() {
        gameStartedView = new GameStartedView();
        Platform.runLater(() -> stage.setScene(gameStartedView.getScene()));

    }

    public void setPossibleMinigames(ArrayList<Integer> minigames) {
        minigameChoicePane.setAvailableMinigames(minigames);
    }

    public void showMiniGameChoiceView() {
        gameView.showMinigameChoice();
    }

    public void sendToServer(String msg) {

    }

    public void close() {
        trickPane.killTimer();
    }


}
