package bonken.net;

import bonken.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private final Controller controller;
    private final int port;
    private final String host;
    private final String username;
    private PrintWriter out;


    private Socket socket;

    public Client(Controller controller, String host, int port, String username) {
        this.controller = controller;
        this.host = host;
        this.port = port;
        this.username = username;
    }


    @Override
    public void run() {
        try {
            socket = new Socket(host, port);
            boolean running = true;
            while (running) {
                // TODO
            }
        } catch (ConnectException ex) {
            logger.log(Level.SEVERE, "Server is not running. {0}", ex.getMessage());
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Client can't connect. {0}", ex.getMessage());
        } finally {
            close();
        }
    }


    public void close() {
        logger.info("Closing client.");
        try {
            //if (out != null) {
            //    sendToServer(Protocol.QUIT, "");          TODO
            //}
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ex) {
            logger.severe(ex.getMessage());
        }
    }



}
