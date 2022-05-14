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
    private String username;
    private MinigameChoicePane minigameChoicePane;
    private CardPane cardPane;
    private TrickPane trickPane;
    private GameView gameView;
    private EndGameView endGameView;

    // online game
    private NameInputView onlineNameInputView;
    private Client client;
    private boolean clientOnly;
    private Server server;
    private static final int PORT_NUMBER = 11111;               // TODO
    private static final String HOST = "localhost";

    public Position myPos;
    private GuiPlayer guiPlayer;
    private NameInputView nameInputView;

    private OnlineController onlineController;

    public Controller(Stage stage) {
        this.stage = stage;
        this.startMenuView = new StartMenuView( () -> { stage.setScene(gameMenuView.getScene());}, () -> {stage.close();this.close();});
        this.gameMenuView = new GameMenuView(this::startGame, () -> { stage.setScene(startMenuView.getScene());}, () -> this.getNameOnline());
        this.minigameChoicePane = new MinigameChoicePane( minigame -> {
            gameView.hideMinigameChoice();
            guiPlayer.minigameSelected(minigame.num);
        });
        this.cardPane = new CardPane(card -> { guiPlayer.cardSelected(card); cardPane.update(); trickPane.packUpTrick(); });
        this.trickPane = new TrickPane(Position.North, () -> gameView.showBlockingRec(), () -> gameView.hideBlockingRec());
        this.endGameView = new EndGameView(() -> { stage.setScene(startMenuView.getScene());}, () -> {stage.close();this.close();});



        this.nameInputView =  new NameInputView(name -> {
            username = name;
            startGame();
        });

        this.onlineNameInputView = new NameInputView(name -> {
            username = name;
            startupBackend();
        });

        this.gameView = new GameView(  minigameChoicePane, cardPane, trickPane);
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

    private void startGame() {

        if(username == null) {
            getName();
            return;
        }

        PlayerInterface[] players = new  PlayerInterface[4];

        guiPlayer = new GuiPlayer(0, Position.North, username,
                minigames -> showMiniGameChoiceView(minigames),
                () -> {
                    trickPane.update();
                    cardPane.update();
                });
        players[0] = guiPlayer;

        for (int i = 1; i < 4; i++) {
            players[i] = new PlayerBot(i, Position.values()[i]);
        }

        game = new Game(players, () -> showEndGameScreen());

        stage.setScene(gameView.getScene());

        game.startRound();

        gameView.setGame(game);
        trickPane.setGame(game);
        endGameView.setGame(game);


    }

    private void startupBackend() {

        client = new Client(this, HOST, PORT_NUMBER, username);
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

        Platform.runLater(() -> {
            onlineController = new OnlineController(stage, client, startMenuView, myPos);
            client.setOnlineController(onlineController);
        });

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
        //TODO make server, player num choice view,...

        System.out.println("------------------------------------------------");
        if (username == null) {
            getNameOnline();
            return;
        }

        PlayerInterface[] players = new  PlayerInterface[4];

        for (int i = 0; i < server.getConnections().size(); i++) {
            NetPlayer player = new NetPlayer(i, Position.values()[i], server.getConnections().get(i).getName(),
                    minigames -> showMiniGameChoiceView(minigames),
                    () -> {
                        trickPane.update();
                        cardPane.update();
                    }, server);
            players[i] = player;
        }

        for (int i = server.getConnections().size(); i < 4; i++) {
            players[i] = new PlayerBot(i, Position.values()[i]);
        }

        game = new Game(players, () -> showEndGameScreen());
        server.setGame(game);

        server.startGame();


    }

    public void showEndGameScreen() {
        endGameView.show();
        stage.setScene(endGameView.getScene());
    }

    public void showMiniGameChoiceView(ArrayList<Integer> availableMinigames) {

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
        if (server != null){
            server.stop();
        }
        trickPane.killTimer();
    }


}
