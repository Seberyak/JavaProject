package ChatRoom.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class OtarasSocketServerThreaded {
    //socket server port on which it will listen
    private static final int PORT = 4321;
    //make static SQL connection for server, use it in BufferInputThread,fill SQL_Connection arguments
//    public static SQL_Connection conn = new SQL_Connection("user","password");

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println(" Waiting for the client request");

        while (true) {
            Socket socket = server.accept();
            ClientsPair clientsPair;

            while (true) {
                clientsPair = new ClientsPair(socket, socket.getPort());
                //if client added to list, break this loop
                if (OnlineClients.getInstance().addClientToList(clientsPair)) break;
            }

            Thread thread = new Thread(new ThreadServer(clientsPair));
            thread.start();
        }
//        server.close();
    }
}