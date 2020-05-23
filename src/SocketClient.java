

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 3443);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

//        System.out.println("Sending out a message.");
        Scanner scanner =new Scanner(System.in);
//        out.println("Test text");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        System.out.println(in.readLine());
        String message;
        while (true){
            message = scanner.nextLine();
            out.println(message);
            System.out.println(in.readLine());
            if(message.equals("exit")){
                out.println("exit");
                break;
            }
        }
        out.close();
//        in.close();
    }
}