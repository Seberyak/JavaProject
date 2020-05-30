package ChatRoom.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadServer implements Runnable {

    ClientsPair client;

    public ThreadServer(ClientsPair client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {

            Socket socket = client.getSocket();
            BufferedReader in = client.getInMsg();
            PrintWriter out = client.getOutMsg();

            System.out.println("ChatRoom.Client with port " + socket.getPort() + " - joined the ChatRoom.");
            System.out.println("ChatRoom.Client with port " + socket.getPort() + " - set name to: " + client.getName());

            OnlineClients.getInstance().sendMsgAll(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), "Chat", client.getName() + " join the chat", 0);
//            System.out.println("ChatRoom.Client port " + socket.getPort());
//            System.out.println("ChatRoom.Server port " + socket.getLocalPort());

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
