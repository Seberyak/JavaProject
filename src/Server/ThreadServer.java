package Server;

import Sever.ScannerThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadServer {


    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocket server = new ServerSocket(4321);

        System.out.println("Waiting for the client request");
        Socket socket = server.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Client port " + socket.getPort());
        System.out.println("Server port " + socket.getLocalPort());

        BufferInputThread input = new BufferInputThread(in,socket,out);
//        Sever.ScannerThread output = new ScannerThread(scanner,out);

        input.start();

        out.println("Hello there!");
//        output.start();

        input.join();
        System.out.println("input interapted: " + input.getState());
//        output.interrupt();

        in.close();
        out.close();

        server.close();
    }
}
