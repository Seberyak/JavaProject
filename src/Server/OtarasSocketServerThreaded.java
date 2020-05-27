package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class OtarasSocketServerThreaded {


    //socket server port on which it will listen
    private static final int PORT = 4321;

    public static void main(String[] args) throws IOException, IOException {
        ServerSocket server = new ServerSocket(PORT);

        System.out.println(" Waiting for the client request");

        while(true) {
            Socket socket = server.accept();

            Thread thread = new Thread(new ThreadServer(socket));
            thread.start();
        }

//        server.close();
    }
}
