package bonken.net;

import bonken.Controller;
import bonken.game.*;
import javafx.application.Platform;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    private final int PORT_NUMBER;
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final Client client;
    Controller controller;
    private final List<Connection> connections;

    private ServerSocket serverSocket;
    private Socket socket;
    boolean gameStarted;

    Game game;

    public Server(Client client, int PORT_NUMBER, Controller controller) {
        this.PORT_NUMBER = PORT_NUMBER;
        this.client = client;
        connections = new ArrayList<>();
        this.controller = controller;
        gameStarted = false;
    }

    public void setGameStarted(){
        gameStarted = true;
    }

    @Override
    public void run() {
        try {
            // start server...
            serverSocket = new ServerSocket(PORT_NUMBER);
            LOGGER.info("The server is running.");
            // ...then client
            new Thread(client).start();
            while (true) {
                // listening for clients...
                    socket = serverSocket.accept();
                    LOGGER.info("The server has accepted connection.");
                    // ...open new connection with client
                    Connection connection = new Connection(this, socket, connections.size());

                    new Thread(connection).start();


            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "The server is not running. {0}", ex.getMessage());
        } finally {
            stop();
        }
    }

    public boolean addConnection(Connection newConnection, String newName) {
        // add only connection with name not yet existing
        synchronized(connections) {
            for (Connection connection : connections) {
                if (connection.getName().equals(newName) || connections.size() >= 4 || gameStarted) {
                    return false;
                }
            }
            LOGGER.info("Adding connection for " + newName);
            connections.add(newConnection);

            //sends myPos to client
            Platform.runLater(() -> newConnection.sendToClient(Protocol.MYPOS, String.valueOf(connections.size()-1)));

            return true;
        }
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void removeConnection(String nameToRemove) {
        synchronized(connections) {
            for (Iterator<Connection> it = connections.iterator(); it.hasNext();) {
                Connection connection = it.next();
                if (connection.getName().equals(nameToRemove)) {
                    LOGGER.info("Removing connection for " + nameToRemove);
                    it.remove();
                }
            }
        }
    }

    public void broadcast(Protocol protocol,String msg) {
        synchronized(connections) {
            for (Connection connection : connections) {
                connection.sendToClient(protocol, msg);
            }
        }
    }

    public void stop() {
        LOGGER.info("Stopping server.");
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "The server has failed to stop properly. {0}", ex.getMessage());
        }
    }

    public void setPLayers(Game game) {
        this.game = game;
        PlayerInterface[] players = new  PlayerInterface[4];

        for (int i = 0; i < this.getConnections().size(); i++) {
            int finalI = i;
            NetPlayer player = new NetPlayer(i, Position.values()[i], this.getConnections().get(i).getName(),
                    minigames -> this.sendMinigamesToClient(finalI, minigames),
                    (cardHand, playableCards) -> {
                        this.sendTrickToClient(finalI, cardHand, playableCards); sendScoreToClients();
                    }, this, (trick, firstPlayer) -> {sendTrickEndToClients(trick, firstPlayer); sendScoreToClients();});
            players[i] = player;
        }

        for (int i = this.getConnections().size(); i < 4; i++) {
            players[i] = new PlayerBot(i, Position.values()[i], (trick, firstPlayer) -> {sendTrickEndToClients(trick, firstPlayer); sendScoreToClients();} );
        }

        game = new Game(players, () -> sendGameEndToClients(), () -> sendStatusToClients());
        setGame(game);
        startGame();
    }

    public void setGame(Game game){
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void startGame(){
        broadcast(Protocol.GAME_STARTED, "");
        game.startRound();
    }

    public void sendStatusToClients() {
        int gameCount = this.game.getGameCounter() + 1;
        if (gameCount > 11) gameCount = 11;
        String status = "";
        status += String.valueOf(gameCount);
        status += "#";
        status += Minigames.values()[this.game.getCurrentRound().getChosenMiniGameNum()].name;
        broadcast(Protocol.ROUND, status);
    }

    public void sendMinigamesToClient(int id, ArrayList<Integer> minigames) {
        ArrayList<Card> cardHand = game.getPlayers()[id].getCardHand().getHand();

        String whole = "";

        String possibleMG = "";
        for (Integer mg: minigames) {
            possibleMG += mg + "#";
        }
        whole += possibleMG;
        whole += "@";

        String cardHandString = "";
        for (Card card : cardHand){
            cardHandString += card.getImage() + "#";
        }
        whole += cardHandString;

        getConnections().get(id).sendToClient(Protocol.POSSIBLE_MINIGAMES, whole);
    }

    public void sendTrickToClient(int id, ArrayList<Card> cardHand, ArrayList<Card> playableCards) {

        Card[] currentTrick = game.getCurrentRound().getCurrentTrick().getCards();
        int firstplayer = game.getCurrentRound().getCurrentTrick().firstPlayer;

        String sendingTrick = "";
        sendingTrick += firstplayer;
        sendingTrick += "@";

        for (Card card: currentTrick) {
            if (card == null) break;
            sendingTrick += card.getImage() + "#";
        }
        sendingTrick += "@";

        for (Card card : cardHand){
            sendingTrick += card.getImage() + "#";
        }

        sendingTrick += "@";
        for (Card card : playableCards){
            sendingTrick += card.getImage() + "#";
        }

        getConnections().get(id).sendToClient(Protocol.TRICK_AND_HAND, sendingTrick);

    }

    public void setMinigame(Integer minigame, int concectionNum) {
        game.getPlayers()[concectionNum].minigameSelected(minigame);
    }

    public void setCard(String card, int connectionNum) {
        int firstPlayer = game.getCurrentRound().getCurrentTrick().firstPlayer;

        Platform.runLater(() -> game.getPlayers()[connectionNum].cardSelected(card));

    }

    public void sendScoreToClients() {
        String players = "";
        String scores = "";
        String whole = "";
        for (int i = 0; i < 4; i++) {
            players += game.getPlayers()[i].getUsername();
            players += "#";
        }
        whole += players;
        whole += "@";
        for (int i = 0; i < 4; i++) {
            scores += String.valueOf(game.getPlayers()[i].getScore());
            scores += "#";
        }
        whole += scores;
        broadcast(Protocol.SCORE, whole);
    }

    public void sendTrickEndToClients(Trick trick, Integer firstPlayer){
        Card[] cards = trick.getCards();
        String whole = "";
        String player = String.valueOf(firstPlayer);
        whole += player;
        whole += "@";
        String trickString = "";
        for (int i = 0; i < 4; i++) {
            trickString += cards[i].getImage();
            trickString += "#";
        }

        whole += trickString;

        broadcast(Protocol.TRICK_END, whole);
    }

    private void sendGameEndToClients() {
        broadcast(Protocol.GAME_ENDED, "");
        this.stop();
    }

}
