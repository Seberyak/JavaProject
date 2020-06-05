package ChatRoom.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class ConnectionThread is used to send all online clients
 * info that new client joined the ChatRoom. Also it creates
 * instance of BufferInputThread and starts this thread.
 *
 * @author Daniel Barbakadze.
 * @author Valera Liparteliani.
 */
public class ConnectionThread implements Runnable {

    ClientsPair client;

    /**
     * Variable client is used to get socket and other
     * meta data.
     *
     * @param client is Class ClientsPair instance.
     */
    public ConnectionThread(ClientsPair client) {
        this.client = client;
    }

    /**
     * Function run Overrides Thread.run(), that starts new thread.
     * This function sends message to all client that new client
     * joined the chat including recently joined client.
     *
     * Variable input is instance of Class BufferInputThread that is
     * incoming messages in Server from clients input. After creating
     * this instance input thread is starting.
     *
     */
    @Override
    public void run() {
        try {

            Socket socket = client.getSocket();
            BufferedReader in = client.getInMsg();
            PrintWriter out = client.getOutMsg();

            System.out.println("ChatRoom.Client with port " + socket.getPort() + " - joined the ChatRoom.");
            System.out.println("ChatRoom.Client with port " + socket.getPort() + " - set name to: " + client.getName());

            OnlineClients.getInstance().sendMsgAll(
                    new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),
                    "Chat",
                    client.getName() + " join the chat",
                    0
            );

            BufferInputThread input = new BufferInputThread(client);

            input.start();
            input.join();

            in.close();
            out.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
