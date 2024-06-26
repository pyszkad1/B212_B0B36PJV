package bonken.net;

import bonken.Controller;
import bonken.OnlineController;
import bonken.game.Position;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client class for net game.
 * Inspired by ladislav.seredi@fel.cvut.cz
 */

public class Client implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    private final Controller controller;
    private final int port;
    private final String host;
    private final String name;

    private Socket socket;
    private PrintWriter out;
    private OnlineController onlineController;

    public Client(Controller controller, String host, int port, String name) {
        this.controller = controller;
        this.host = host;
        this.port = port;
        this.name = name;
    }

    public void setOnlineController(OnlineController onlineController) {
        this.onlineController = onlineController;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            boolean running = true;
            while (running) {
                String msg = in.readLine();
                LOGGER.log(Level.INFO, "Client received: >>>{0}<<<", msg);
                if (msg != null) {
                    processIncomingMessage(msg);
                } else {
                    running = false;
                }
            }
        } catch (ConnectException ex) {
            LOGGER.log(Level.SEVERE, "Server is not running. {0}", ex.getMessage());
            //controller.showAlert("Can't connect to server.");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Client can't connect. {0}", ex.getMessage());
            //controller.showAlert("Connection to server lost.");
        } finally {
            close();
        }
    }

    private void processIncomingMessage(String msg) {
        String[] tokens = msg.split("\\|"); // escape pipe in regexp
        Protocol actionCode = Protocol.valueOf(tokens[0]);
        String actionPayload = tokens.length > 1 ? tokens[1] : "";
        switch (actionCode) {
            case SUBMIT:
                sendToServer(Protocol.USERNAME, name);
                break;
            case ACCEPTED:
                break;
            case REJECTED:
                controller.showStartMenu();
                break;
            case MYPOS:
                Platform.runLater(() -> onlineController.setMyPos(Position.values()[Integer.valueOf(tokens[1])]));
                break;
            case GAME_STARTED:
                onlineController.showGameStarted();
                break;
            case POSSIBLE_MINIGAMES:
                String[] minigamesAndHand = tokens[1].split("@");
                String[] minigamesString = minigamesAndHand[0].split("#");
                String[] cardHandString = minigamesAndHand[1].split("#");

                ArrayList<Integer> minigames = new ArrayList<>();
                for (String minigame: minigamesString) {
                    minigames.add(Integer.valueOf(minigame));
                }

                Platform.runLater(() -> {onlineController.showCardHand(cardHandString); onlineController.showGameView(); onlineController.setPossibleMinigames(minigames);
                                        onlineController.showMiniGameChoiceView();});
                break;
            case TRICK_AND_HAND:
                String[] trickAndHand = tokens[1].split("@");
                String firstPlayer = trickAndHand[0];
                String[] trick = trickAndHand[1].split("#");
                String[] hand = trickAndHand[2].split("#");
                String[] playableCards = trickAndHand[3].split("#");
                onlineController.setCurrentStringCardHand(hand);
                Platform.runLater(() -> {onlineController.showGameView(); onlineController.updateGui(firstPlayer ,trick, hand, playableCards);});
                break;
            case TRICK_END:
                String[] playerAndTrick = actionPayload.split("@");
                String firstPlayerAgain = playerAndTrick[0];
                String[] wholeTrick = playerAndTrick[1].split("#");
                Platform.runLater(() -> onlineController.updateTrickEnd(firstPlayerAgain, wholeTrick));
                break;
            case ROUND:
                String[] statusInfo = actionPayload.split("#");
                String roundNum = statusInfo[0];
                String minigame = statusInfo[1];
                Platform.runLater(() -> onlineController.updateStatus(roundNum, minigame));
                break;
            case SCORE:
                String[] scoreBoard = actionPayload.split("@");
                String[] players = scoreBoard[0].split("#");
                String[] score = scoreBoard[1].split("#");
                Platform.runLater(() -> onlineController.updateScoreboard(players, score));
                break;
            case GAME_ENDED:
                Platform.runLater(() -> onlineController.showEndGameScreen());
                break;
        }
    }

    /**
     *
     * @param code Protocol code to send
     * @param payload message to send
     */
    public void sendToServer(Protocol code, String payload) {
        String msg = code.toString() + '|' + payload;
        LOGGER.log(Level.INFO, "Client {1} is sending >>>{0}<<< to server", new Object[]{msg, name});
        out.println(msg);
    }

    public void close() {
        LOGGER.info("Closing client.");
        try {
            if (out != null) {
                sendToServer(Protocol.QUIT, "");
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }
}
