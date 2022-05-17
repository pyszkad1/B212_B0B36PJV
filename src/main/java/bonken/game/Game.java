package bonken.game;

import bonken.utils.Callable;
import javafx.application.Platform;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class Game {
    PlayerInterface[] players;
    Deck deck;
    ArrayList<Integer> minigames = new ArrayList<>(12);
    ScoreBoard scoreBoard;
    public boolean gameEnded;
    public ArrayList<Round> rounds;
    private int gameCounter;

    private Callable onGameEnd;
    private GameSave gameSave;

    Timer timer;
    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

    public ArrayList<Integer> getMinigames() {
        return minigames;
    }

    public PlayerInterface[] getPlayers() {
        return players;
    }

    public Round getCurrentRound() {
        return this.rounds.get(rounds.size() - 1);
    }

    public int getGameCounter(){
        return  gameCounter;
    }

    private Callable onStatusUpdateNeeded;
    public Game(PlayerInterface[] players, Callable onGameEnd, Callable onStatusUpdateNeeded) {
        for (int i = 0; i < 12; i++) {
            minigames.add(Integer.valueOf(i));
        }
        this.onGameEnd = onGameEnd;
        this.onStatusUpdateNeeded = onStatusUpdateNeeded;
        this.players = players;

        int startingPlayer = 0 ;

        players[startingPlayer].setHisTurn(true);

        rounds = new ArrayList<>();
        scoreBoard = new ScoreBoard(players);
        deck = new Deck();
        gameCounter = 0;
    }

    int startingPlayer = 0;

    public Game(PlayerInterface[] players, Callable onGameEnd, boolean fromGameSave) {
        gameSave = new GameSave();
        if (fromGameSave) {
            gameSave = gameSave.readGame();
            if (gameSave == null) {
                fromGameSave = false;
                players[0].setUsername("Player");
                setGame(players, onGameEnd);
            } else {
                setGameFromFile(players, onGameEnd);
            }
        }
        else {
            setGame(players, onGameEnd);
        }
        players[startingPlayer].setHisTurn(true);
        scoreBoard = new ScoreBoard(players);
        rounds = new ArrayList<>();
        deck = new Deck();
        gameCounter = 11 - minigames.size();
    }

    public void setGameFromFile(PlayerInterface[] players, Callable onGameEnd) {

        minigames = gameSave.getAvailableMinigames();

        this.onGameEnd = onGameEnd;
        this.players = players;
        for (int i = 0; i < 4; i++) {
            players[i].setChosenPositive(gameSave.getPlayersChosenPos().get(i));
            players[i].setScore(gameSave.getScore().get(i));
            players[i].setUsername(gameSave.getUsernames().get(i));
        }
        startingPlayer = gameSave.getStartingPlayer().get(0);
    }

    public void setGame(PlayerInterface[] players, Callable onGameEnd) {
        for (int i = 0; i < 12; i++) {
            minigames.add(Integer.valueOf(i));
        }
        this.onGameEnd = onGameEnd;
        this.players = players;
    }

    public void startRound() {
        deck.shuffle();
        Round round;
        if (onStatusUpdateNeeded != null) {
            round = new Round(this, deck, minigames, players, onStatusUpdateNeeded);
        } else {
            round = new Round(this, deck, minigames, players);
        }
        rounds.add(round);
        round.playRound(() -> finishRound());
    }

    private void finishRound()
    {
        System.out.println(scoreBoard.toString());
        if (gameSave != null) {
            LOGGER.info("Saving game.");
            gameSave.saveGame(this);
        }
        timer = new Timer();

        if(++gameCounter == 1) {                         // TODO == 11
            gameEnded = true;
            LOGGER.info("END OF GAME");


            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> onGameEnd.call());
                }} , 3100);
            return;
        }

        startRound();
    }

    public Deck getDeck(){
        return deck;
    }

    public void killTimer(){
        if (timer != null) {
            timer.cancel();
        }
    }

}
