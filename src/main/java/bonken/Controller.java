package bonken;

import bonken.game.*;
import bonken.gui.*;
import bonken.net.Client;
import bonken.net.Server;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Controller {
    Game game;
    public Stage stage;

    private StartMenuView startMenuView;
    private GameMenuView gameMenuView;
    private GameSetupView gameSetupView;
    private String username;
    private MinigameChoicePane minigameChoicePane;
    private CardPane cardPane;
    private OfflineTrickPane trickPane;
    private GameView gameView;
    private EndGameView endGameView;

    // online game
    private NameInputView onlineNameInputView;
    private Client client;
    private boolean clientOnly;
    private Server server;
    private static final int PORT_NUMBER = 11111;
    private static final String HOST = "localhost";

    public Position myPos;
    private GuiPlayer guiPlayer;
    private NameInputView nameInputView;

    private OnlineController onlineController;

    public Controller(Stage stage) {
        this.stage = stage;
        this.startMenuView = new StartMenuView( () -> { stage.setScene(gameMenuView.getScene());}, () -> {stage.close();this.close();});
        this.gameMenuView = new GameMenuView(() -> stage.setScene(gameSetupView.getScene()), () -> { stage.setScene(startMenuView.getScene());}, () -> this.getNameOnline());
        this.gameSetupView = new GameSetupView(() -> stage.setScene(startMenuView.getScene()), () -> startGame(false), () -> loadGame());

        this.endGameView = new EndGameView(() -> { stage.setScene(startMenuView.getScene());}, () -> {stage.close();this.close();});

        this.nameInputView =  new NameInputView(name -> {
            username = name;
            startGame(false);
        });


        this.onlineNameInputView = new NameInputView(name -> {
            username = name;
            startupBackend();
        });

    }

    public void start() {
        stage.setScene(startMenuView.getScene());
        stage.show();
    }

    public void getName() {
        stage.setScene(nameInputView.getScene());
    }

    public void getNameOnline() {
        stage.setScene(onlineNameInputView.getScene());
    }

    public void showStartMenu() {
        //TODO IF COMING FROM ONLINE REFUSAL SHOW MSG!!
        Platform.runLater(() -> {
            stage.setScene(startMenuView.getScene());
        });
    }

    private void loadGame() {
        startGame(true);
    }

    private void startGame(boolean fromGameSave) {
        this.minigameChoicePane = new MinigameChoicePane( minigame -> {
            gameView.hideMinigameChoice();
            guiPlayer.minigameSelected(minigame.num);
        });
        this.cardPane = new CardPane(card -> { guiPlayer.cardSelected(card); cardPane.updateAfter(guiPlayer.getStringHand()); trickPane.packUpTrick(); });
        this.trickPane = new OfflineTrickPane(Position.North, () -> gameView.showBlockingRec(), () -> gameView.hideBlockingRec());
        this.gameView = new GameView( minigameChoicePane, cardPane, trickPane);

        PlayerInterface[] players = new  PlayerInterface[4];

        guiPlayer = new GuiPlayer(0, Position.North, username,
                minigames -> showMiniGameChoiceView(minigames),
                () -> {
                    trickPane.update();
                    updateCards();
                });
        players[0] = guiPlayer;

        for (int i = 1; i < 4; i++) {
            players[i] = new PlayerBot(i, Position.values()[i]);
        }

        if (fromGameSave) {
            game = new Game(players, () -> showEndGameScreen(), fromGameSave);
        } else {
            if(username == null) {
                getName();
                return;
            }
            game = new Game(players, () -> showEndGameScreen(), fromGameSave);

        }

        guiPlayer.setGame(game);
        stage.setScene(gameView.getScene());
        trickPane.setGame(game);
        endGameView.setGame(game);
        game.startRound();
    }

    private void updateCards(){
        Platform.runLater(() ->
        {cardPane.updateBefore(guiPlayer.getStringHand(), guiPlayer.getStringPlayableCards());}
        );
    }

    private void startupBackend() {

        client = new Client(this, HOST, PORT_NUMBER, username);
        onlineController = new OnlineController(stage, client, startMenuView, this);
        client.setOnlineController(onlineController);
        System.out.println("username is " + username);
        if (!isServerRunning()) {
            server = new Server(client, PORT_NUMBER, this);
            startServerThenClient();
            clientOnly = false;
            System.out.println("You own the server");

            showSettingUpOnlineGame();
        } else {
            startClient();
            clientOnly = true;
            System.out.println("waiting");
            showSettingUpOnlineGame();
        }





    }

    private void startServerThenClient() {
        new Thread(server).start();
    }

    private void startClient() {
        new Thread(client).start();
    }

    private StartOnlineView startOnlineView;
    private WaitingView waitingView;

    public void showSettingUpOnlineGame() {
        if (clientOnly){
            System.out.println("I am just a client");
            waitingView = new WaitingView();
            stage.setScene(waitingView.getScene());
        }else{
        startOnlineView = new StartOnlineView(() -> {startGameOnline(); server.setGameStarted();});
        stage.setScene(startOnlineView.getScene());
        }
    }

    private void startGameOnline() {

        System.out.println("------------------------------------------------");
        if (username == null) {
            getNameOnline();
            return;
        }
        server.setPLayers(game);
    }

    public void showEndGameScreen() {
        endGameView.show();
        stage.setScene(endGameView.getScene());
    }

    public void showMiniGameChoiceView(ArrayList<Integer> availableMinigames) {
        ArrayList<Card> cardHand = game.getPlayers()[0].getCardHand().getHand();
        String[] hand = new String[cardHand.size()];

        for (int i = 0; i < cardHand.size(); i++) {
            hand[i] = cardHand.get(i).getImage();
        }

        cardPane.updateAfter(hand);
        minigameChoicePane.setAvailableMinigames(availableMinigames);
        gameView.showMinigameChoice();
    }

    private boolean isServerRunning() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            serverSocket.close();
            return false;
        } catch (IOException ex) {
            return true;
        }
    }

    public void close() {
        if (server != null) {
            Platform.runLater(() -> server.stop());
        }
        if (trickPane != null) trickPane.killTimer();
        if (game != null) game.killTimer();
        System.exit(0);
    }


}
