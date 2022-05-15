package bonken.net;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ladislav.seredi@fel.cvut.cz
 */
public class Connection implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Connection.class.getName());

    private final Server server;
    private final Socket socket;
    private PrintWriter out;
    private String name;

    private int connectionNum;

    public Connection(Server server, Socket socket, int connectionNum) {
        this.server = server;
        this.socket = socket;
        this.connectionNum = connectionNum;
    }

    @Override
    public void run() {
        try {
            // setup connection
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            // send first communication to clients: SUBMIT (i.e. request to submit nickname)
            sendToClient(Protocol.SUBMIT, "");
            boolean running = true;
            while (running) {
                // read message from client
                String msg = in.readLine();
                LOGGER.log(Level.INFO, "Server received: >>>{0}<<<", msg);
                if (msg != null) {
                    running = processIncomingMessage(msg);
                } else {
                    // end communication if received null
                    running = false;
                }
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error communicating with client {0}", ex.getMessage());
        } finally {
            quit();
        }
    }

    private boolean processIncomingMessage(String msg) {
        String[] tokens = msg.split("\\|"); // escape pipe in regexp
        Protocol actionCode = Protocol.valueOf(tokens[0]);
        String actionPayload = tokens.length > 1 ? tokens[1] : "";
        switch (actionCode) {
            // incoming message arrived, decide what to do
            case USERNAME:
                if (server.addConnection(this, actionPayload)) {
                    // name sent, add connection to the list maintained by server
                    name = actionPayload;
                    sendToClient(Protocol.ACCEPTED, "");
                    System.out.println("ADDED CONNECTION");
                } else {
                    // connection with this name already there, reject
                    sendToClient(Protocol.REJECTED, "");
                }
                break;
            case QUIT:
                //TODO SEND other players that game is no longer playable or somthing?
                server.removeConnection(name);
                return false;
            case MINIGAME:
                Integer chosenMg = Integer.valueOf(actionPayload);
                server.setMinigame(chosenMg, connectionNum);
            case CARD:
                server.setCard(actionPayload, connectionNum);
        }
        return true;
    }

    public void sendToClient(Protocol code, String payload) {
        String msg = code.toString() + '|' + payload;
        LOGGER.log(Level.INFO, "Sending >>>{0}<<< to {1}", new Object[]{msg, name});
        out.println(msg);
    }

    private void quit() {
        LOGGER.info("Quitting connection.");
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    public String getName() {
        return name;
    }
}
