package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ThreadServer {


    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocket server = new ServerSocket(4321);

        System.out.println("Waiting for the client request");
        Socket socket = server.accept();

        BreakClass Break = new BreakClass();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner =new Scanner(System.in);

        System.out.println("Client port " + socket.getPort());
        System.out.println("Server port " + socket.getLocalPort());

        BufferInputThread input = new BufferInputThread(in,Break,out);
        ScannerThread output = new ScannerThread(scanner,out,Break);

        input.start();
        output.start();

        input.join();
        System.out.println("input interapted: " + input.getState());
        output.interrupt();

        System.out.println("output interapted:" + output.getState());
        in.close();
        out.close();
        scanner.close();


//        while (true){
//            if(!input.isAlive()){ output.interrupt(); break;}
//        }

        server.close();
    }
}
