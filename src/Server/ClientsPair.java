package Server;

import java.net.Socket;

public class ClientsPair {
    Socket socket;
    int port;
    String name;
    public ClientsPair(Socket socket,int port, String name){
        this.socket = socket;
        this.port = port;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
