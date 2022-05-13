package bonken.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable{

    private final int PORT_NUMBER;
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private final Client client;
    private final List<Connection> connections;

    private ServerSocket serverSocket;
    private Socket socket;
    private boolean gameStarted;

    public Server(Client client, int PORT_NUMBER) {
        this.PORT_NUMBER = PORT_NUMBER;
        this.client = client;
        connections = new ArrayList<>();
        gameStarted = false;
    }


    @Override
    public void run() {
        try {
            // start server
            serverSocket = new ServerSocket(PORT_NUMBER);
            logger.info("The server is running.");
            // ...then client
            new Thread(client).start();
            while (connections.size() <= 4 && !gameStarted) {
                // listening for clients...
                socket = serverSocket.accept();
                logger.info("The server has accepted connection.");
                // ...open new connection with client
                Connection connection = new Connection(this, socket);
                new Thread(connection).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void stop() {
        logger.info("Stopping server.");
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "The server has failed to stop properly. {0}", ex.getMessage());
        }
    }



}
