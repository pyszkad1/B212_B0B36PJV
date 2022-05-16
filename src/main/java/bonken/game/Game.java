package bonken.game;

import bonken.net.Server;
import bonken.utils.Callable;
import javafx.application.Platform;

import java.util.*;
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

    Timer timer;
    private static final Logger logger = Logger.getLogger(Server.class.getName());

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

    /*
    public Game(PlayerInterface[] players, Callable onGameEnd) {
        for (int i = 0; i < 12; i++) {
            minigames.add(Integer.valueOf(i));
        }
        this.onGameEnd = onGameEnd;
        this.players = players;

        Random random = new Random();
        int startingPlayer = 0 ;// random.nextInt( 4);

        players[startingPlayer].setHisTurn(true);

        rounds = new ArrayList<>();
        scoreBoard = new ScoreBoard(players);
        deck = new Deck();
        gameCounter = 0;
    }
    */

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

    public Game(PlayerInterface[] players, Callable onGameEnd, boolean fromGameSave) {
        int startingPlayer = 0;
        if (fromGameSave) {
            GameSave gameSave = new GameSave();
            gameSave.readGame();
            minigames = gameSave.getAvailableMinigames();

            this.onGameEnd = onGameEnd;
            this.players = players;
            for (int i = 0; i < 4; i++) {
                players[i].setChosenPositive(gameSave.getPlayersChosenPos()[i]);
                players[i].setScore(gameSave.getScore()[i]);
            }
            startingPlayer = gameSave.getStartingPlayer();

        }
        else {
            for (int i = 0; i < 12; i++) {
                minigames.add(Integer.valueOf(i));
            }

            this.onGameEnd = onGameEnd;
            this.players = players;

        }

        players[startingPlayer].setHisTurn(true);
        scoreBoard = new ScoreBoard(players);
        rounds = new ArrayList<>();
        deck = new Deck();
        gameCounter = 11 - minigames.size();
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
        System.out.println("--------------------------------------------------------------------------------------------");
        round.playRound(() -> finishRound());
    }

    private void finishRound()
    {
        System.out.println(scoreBoard.toString());
        timer = new Timer();

        if(++gameCounter == 11) {                               // TODO == 11
            gameEnded = true;
            logger.info("END OF GAME");


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
