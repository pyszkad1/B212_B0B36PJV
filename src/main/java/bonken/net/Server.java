package bonken.net;

import bonken.Controller;
import bonken.game.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ladislav.seredi@fel.cvut.cz
 */
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
            newConnection.sendToClient(Protocol.MYPOS, String.valueOf(connections.size()-1));

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
                    cardhand -> {
                        this.sendTrickToClient(finalI, cardhand); trickPane.update(); cardPane.update(finalI, ); //SEND TO CLIENT
                          //SEND TO CLIENT
                    }, this);
            players[i] = player;
        }

        for (int i = this.getConnections().size(); i < 4; i++) {
            players[i] = new PlayerBot(i, Position.values()[i]);
        }

        game = new Game(players, () -> controller.showEndGameScreen());
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

    public void sendMinigamesToClient(int id, ArrayList<Integer> minigames) {
        String possibleMG = "";
        for (Integer mg: minigames) {
            possibleMG += mg + "#";
        }
        //TODO Poslat i karty!!
        getConnections().get(id).sendToClient(Protocol.POSSIBLE_MINIGAMES, possibleMG);
    }

    public void sendTrickToClient(int id, ArrayList<Card> cardHand) {
        Card[] currentTrick = game.getCurrentRound().getCurrentTrick().getCards();

        String sendingTrick = "";
        for (Card card: currentTrick) {
            sendingTrick += card.getImage() + "#";
        }
        sendingTrick += "@";

        for (Card card : cardHand){
            sendingTrick += card.getImage() + "#";
        }

        getConnections().get(id).sendToClient(Protocol.TRICK_AND_HAND, sendingTrick);

    }

    public void setMinigame(Integer minigame, int concectionNum) {
        game.getPlayers()[concectionNum].minigameSelected(minigame);
    }

    public void setCard(String card, int connectionNum) {
        game.getPlayers()[connectionNum].cardSelected(card);
    }

}
