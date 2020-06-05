package ChatRoom.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *  Class ClientsPair is used to create new object
 *  that serves every information about the client
 *  including socket, port, name, printWriter,
 *  BufferedReader and methods.
 *
 *
 *  @author Daniel Barbakadze.
 *  @author Valera Liparteliani.
 */
public class ClientsPair {
    private Socket socket;
    private int port;
    private String name;
    private PrintWriter outMsg;
    private BufferedReader inMsg;

    /**
     * ClientsPair constructor sets socket and port
     * from given socket and port variable.
     *
     * This method also sends message to new client
     * and is waiting for a clients name to save it.
     * First received input is client's name and every message
     * after that is actually the ChatRoom message.
     *
     * @param socket is client's socket.
     * @param port is client's port.
     */
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

    /**
     * Get Clients socket outside the class.
     *
     * @return socket;
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Get Clients port outside the class.
     *
     * @return port;
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets message from the server.
     *
     * @return outMsg;
     */
    public PrintWriter getOutMsg() {
        return outMsg;
    }

    /**
     * Gets message from the client.
     *
     * @return inMsg;
     */
    public BufferedReader getInMsg() {
        return inMsg;
    }

    /**
     * Get Clients name outside the class.
     *
     * @return name;
     */
    public String getName() {
        return name;
    }

    /**
     * This custom method overrides toString() method.
     *
     * @return ClientsPair instance formatted as an object.
     */
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
