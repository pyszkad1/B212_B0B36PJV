package bonken.net;

import bonken.Controller;
import bonken.game.Game;
import bonken.game.Position;

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
                    Connection connection = new Connection(this, socket);

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


    public void setGame(Game game){
        this.game = game;
    }

    public void startGame(){
        broadcast(Protocol.GAME_STARTED, "");
//POSLAT KARTY
        game.startRound();
    }

    public void sendMinigamesToClient(int id, ArrayList<Integer> minigames) {
        String possibleMG = "";
        for (Integer mg: minigames) {
            possibleMG += mg + "#";
        }
        getConnections().get(id).sendToClient(Protocol.POSSIBLE_MINIGAMES, possibleMG);

    }

}
