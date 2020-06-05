package ChatRoom.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class SocketServerThreaded creates new ClientsPair instance and
 * based on it creates a new ConnectionThread thread for client.
 *
 * @author Daniel Barbakadze.
 * @author Valera Liparteliani.
 */
public class SocketServerThreaded {

    private static final int PORT = 4321;
//    make static SQL connection for server, use it in BufferInputThread,fill SQL_Connection arguments


    public static SQL_Connection conn = new SQL_Connection("root","password");

    /**
     * Function main tries to create new ServerSocket as a instance called server.
     *
     * Server is waiting for a new connection. When new connection appears it
     * creates a new ClientsPair instance and pass it a socket and a client's port.
     * After that it creates a net ConnectionThread thread for this client.
     *
     * @param args are given from terminal.
     */
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println(" Waiting for the client request");

            while (true) {
                Socket socket = server.accept();
                ClientsPair clientsPair;

                while (true) {
                    clientsPair = new ClientsPair(socket, socket.getPort());
                    // if client added to list, break this loop.
                    if (OnlineClients.getInstance().addClientToList(clientsPair)) break;
                }
                Thread thread = new Thread(new ConnectionThread(clientsPair));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        server.close();
    }
}