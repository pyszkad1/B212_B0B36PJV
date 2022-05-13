package bonken.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import bonken.Controller;
import bonken.game.Position;
import bonken.net.Protocol;
import javafx.application.Platform;

/**
 *
 * @author ladislav.seredi@fel.cvut.cz
 */
public class Client implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final Controller controller;
    private final int port;
    private final String host;
    private final String name;

    private Socket socket;
    private PrintWriter out;

    public Client(Controller controller, String host, int port, String name) {
        this.controller = controller;
        this.host = host;
        this.port = port;
        this.name = name;
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
            LOGGER.log(Level.SEVERE, "Client can''t connect. {0}", ex.getMessage());
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
                Platform.runLater(() -> { // dtto as above
            //        controller.showChatWindow(name);
                });
                break;
            case REJECTED:
            //    controller.showAlert("Name " + name + " already taken. Please choose another one.");
                break;
            case MYPOS:
                controller.myPos = Position.values()[Integer.valueOf(tokens[1])];
                System.out.println("myPos je " + controller.myPos);
                break;
        }
    }

    public void sendMessage(String message) {
    //    sendToServer(Protocol.BROADCAST, name + " says: " + message);
    }



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
