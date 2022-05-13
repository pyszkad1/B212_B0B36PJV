package bonken.net;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection implements Runnable {

    private final Server server;
    private final Socket socket;
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public Connection(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {

    }

    //TODO
    public void sendToClient(Protocol code, String payload) {
        //String msg = code.toString() + '|' + payload;
        //logger.log(Level.INFO, "Sending >>>{0}<<< to {1}", new Object[]{msg, name});
        //out.println(msg);
    }


    private void quit() {
        logger.info("Quitting connection.");
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ex) {
            logger.severe(ex.getMessage());
        }
    }


}
