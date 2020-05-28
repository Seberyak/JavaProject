package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class BufferInputThread extends Thread {
    BufferedReader bufferedReader;
    Socket socket;
    PrintWriter printWriter;

    public BufferInputThread(BufferedReader sc, Socket socket, PrintWriter printWriter) {
        this.bufferedReader = sc;
        this.socket = socket;
        this.printWriter = printWriter;
    }


    @Override
    public void run() {
        Thread.currentThread().setName("Client.BufferInputThread");
        String in;
        String username = "temp";
        int myport = 0;
//        System.out.println(Thread.currentThread().getName());
        try {
            String name = bufferedReader.readLine();
            for (ClientsPair client : OnlineClients.getClientsPairList()) {
                if (client.getPort() == socket.getPort()) {
                    client.setName(name);
                    username = name;
                    myport = client.getPort();
                }
            }
            printWriter.println("Chat: Welcome " + username + ". You're in chat now.");
            System.out.println("Client with port " + myport + " - set name to: " + username);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!isInterrupted()) {
            try {
                in = bufferedReader.readLine();

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String currentTime = formatter.format(date).toString();

                // send msg to all
                OnlineClients.sendMsgAll(currentTime, username, in, myport);

                System.out.println(currentTime + " " + username + ": " + in);
                if (in.equals("exit")) {

                    bufferedReader.close();
                    printWriter.close();
                    socket.close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        String ClientLeftMessageForServer = "";
        for (ClientsPair client : OnlineClients.getClientsPairList()) {
            if (client.getPort() == socket.getPort()) {
                ClientLeftMessageForServer = client.name + " left the chat.";
            }
        }
        System.out.println(ClientLeftMessageForServer);
    }
}
