package ChatRoom.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientsPair {
    private Socket socket;
    private int port;
    private String name;
    private PrintWriter outMsg;
    private BufferedReader inMsg;

    public ClientsPair(Socket socket, int port) {
        this.socket = socket;
        this.port = port;
        try {
            this.outMsg = new PrintWriter(socket.getOutputStream(), true);
            this.inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outMsg.println("ChatRoom.Server: Hello in our ChatRoom! Before joining chat please tell us your name...");

            name = inMsg.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getPort() {
        return port;
    }


    public void setPort(int port) {
        this.port = port;
    }

    public PrintWriter getOutMsg() {
        return outMsg;
    }

    public BufferedReader getInMsg() {
        return inMsg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClientsPair{" +
                "socket=" + socket +
                ", port=" + port +
                ", name='" + name + '\'' +
                ", outMsg=" + outMsg +
                '}';
    }
}
