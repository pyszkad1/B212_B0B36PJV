package bonken;

import bonken.game.*;
import bonken.gui.*;
import bonken.net.Client;
import bonken.net.Server;
import bonken.utils.Action;
import bonken.utils.Callable;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Controller {
    Game game;
    Stage stage;

    StartMenuView startMenuView;
    GameMenuView gameMenuView;

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


    private GuiPlayer guiPlayer;
    private NameInputView nameInputView;

    public Controller(Stage stage) {
        this.stage = stage;
        this.startMenuView = new StartMenuView( () -> { stage.setScene(gameMenuView.getScene());}, stage::close);
        this.gameMenuView = new GameMenuView(this::startGame, () -> { stage.setScene(startMenuView.getScene());}, () -> this.startupBackend());
        this.minigameChoicePane = new MinigameChoicePane( minigame -> {
            gameView.hideMinigameChoice();
            guiPlayer.minigameSelected(minigame.num);
        });
        this.cardPane = new CardPane(card -> { guiPlayer.cardSelected(card); cardPane.update(); trickPane.packUpTrick(); });
        this.trickPane = new TrickPane(Position.North, () -> gameView.showBlockingRec(), () -> gameView.hideBlockingRec());
        this.endGameView = new EndGameView(() -> { stage.setScene(startMenuView.getScene());}, stage::close);



        this.nameInputView =  new NameInputView(name -> {
            username = name;
            startGame();
        });

        this.onlineNameInputView = new NameInputView(name -> {              // TODO online
            username = name;
            startupBackend();
            showStartOnlineView();
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
                }                );
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
        getNameOnline();
        client = new Client(this, HOST, PORT_NUMBER, username);
        if (!isServerRunning()) {
            server = new Server(client, PORT_NUMBER);
            startServerThenClient();
            clientOnly = false;
        } else {
            startClient();
            clientOnly = true;
        }

    }

    private void startServerThenClient() {
        new Thread(server).start();
    }

    private void startClient() {
        new Thread(client).start();
    }

    private StartOnlineView startOnlineView;

    public void showStartOnlineView() {
        startOnlineView = new StartOnlineView(() -> startGameOnline());
        stage.setScene(startOnlineView.getScene());
    }

    private void startGameOnline() {
        //TODO make server, player num choice view,...
        System.out.println("------------------------------------------------");
        if (username == null) {
            getNameOnline();
            return;
        }

        PlayerInterface[] players = new  PlayerInterface[4];

        guiPlayer = new GuiPlayer(0, Position.North, username,
                minigames -> showMiniGameChoiceView(minigames),
                () -> {
                    trickPane.update();
                    cardPane.update();
                }                );
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

    public void showEndGameScreen() {
        endGameView.show();
        stage.setScene(endGameView.getScene());
    }

    private void showMiniGameChoiceView(ArrayList<Integer> availableMinigames) {

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


}
