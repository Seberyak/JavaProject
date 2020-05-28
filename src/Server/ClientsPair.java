package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientsPair {
    Socket socket;
    int port;
    String name;
    private PrintWriter outMsg;
    public ClientsPair(Socket socket,int port, String name){
        this.socket = socket;
        this.port = port;
        this.name = name;
        try {
            this.outMsg = new PrintWriter(socket.getOutputStream(),true);
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

    public PrintWriter getOutMsg() {
        return outMsg;
    }

    public void setPort(int port) {
        this.port = port;
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
