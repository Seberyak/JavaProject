package Server;

import Sever.ScannerThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadServer implements Runnable {

    ClientsPair client;

    public ThreadServer(ClientsPair client) {
        this.client = client;
    }


    @Override
    public void run() {
        OnlineClients onlineClients = new OnlineClients();
        try {

            Socket socket = client.getSocket();
            BufferedReader in = client.getInMsg();
            PrintWriter out = client.getOutMsg();


            System.out.println("Client with port " + socket.getPort() + " - joined the ChatRoom.");
//            System.out.println("Client port " + socket.getPort());
//            System.out.println("Server port " + socket.getLocalPort());

            BufferInputThread input = new BufferInputThread(client);
//        Sever.ScannerThread output = new ScannerThread(scanner,out);
            input.start();


//        output.start();

            input.join();
//        System.out.println("input interapted: " + input.getState());
//        output.interrupt();

            in.close();
            out.close();
            socket.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) throws IOException, InterruptedException {

//        ServerSocket server = new ServerSocket(4321);
//
//        System.out.println("Waiting for the client request");
//        Socket socket = server.accept();

//    }
}
