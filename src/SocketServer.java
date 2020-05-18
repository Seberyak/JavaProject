import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(4322);

        System.out.println("Waiting for the client request");
        Socket socket = server.accept();

        System.out.println("Client port " + socket.getPort());
        System.out.println("Server port " + socket.getLocalPort());

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String message = in.readLine();

            System.out.println("Message: " + message);

            out.println(message);

            if (message.equals("exit")) {
                break;
            }
        }

        out.close();
        in.close();
        socket.close();

        server.close();
    }
}