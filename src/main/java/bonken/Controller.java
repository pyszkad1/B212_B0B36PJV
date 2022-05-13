package bonken;

import bonken.game.*;
import bonken.gui.*;
import bonken.utils.Action;
import bonken.utils.Callable;
import javafx.application.Platform;
import javafx.stage.Stage;

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


    private GuiPlayer guiPlayer;
    private NameInputView nameInputView;

    public Controller(Stage stage) {
        this.stage = stage;
        this.startMenuView = new StartMenuView( () -> { stage.setScene(gameMenuView.getScene());}, stage::close);
        this.gameMenuView = new GameMenuView(this::startGame, () -> { stage.setScene(startMenuView.getScene());});
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

        this.gameView = new GameView(  minigameChoicePane, cardPane, trickPane);
    }

    public void start() {
        stage.setScene(startMenuView.getScene());
        stage.show();
    }

    public void getName() {
        stage.setScene(nameInputView.getScene());
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

    private void startGameOnline() {
        //TODO make server, player num choice view,...

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

    public void showEndGameScreen() {
        endGameView.show();
        stage.setScene(endGameView.getScene());
    }

    private void showMiniGameChoiceView(ArrayList<Integer> availableMinigames) {

        minigameChoicePane.setAvailableMinigames(availableMinigames);
        gameView.showMinigameChoice();
    }

}
