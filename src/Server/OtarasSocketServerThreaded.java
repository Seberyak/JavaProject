package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

public class OtarasSocketServerThreaded {
    //socket server port on which it will listen
    private static final int PORT = 4321;
    static ArrayList<ClientsPair> clientsPairList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println(" Waiting for the client request");

        while(true) {
            Socket socket = server.accept();

            ClientsPair clientsPair = new ClientsPair(socket,socket.getPort(),"tempName");
            OnlineClients.setClientsPairList(clientsPair);
//            OnlineClients.getClientsPairList().forEach( e -> System.out.println(e.socket));

            Thread thread = new Thread(new ThreadServer(socket));
            thread.start();
        }

//        server.close();
    }
}