

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements java Socket server in a multi-threaded fashion.
 * To test use telnet(s).
 */
public class SocketServerThreaded {

    //socket server port on which it will listen
    private static final int PORT = 4321;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);

        System.out.println(" Waiting for the client request");

        while(true) {
            Socket socket = server.accept();

            Thread thread = new Thread(new SocketThread(socket));
            thread.start();
        }

//        server.close();
    }
}