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
    PrintWriter printWriter;
    ClientsPair client;

    public BufferInputThread(ClientsPair client) {
        this.client =   client;
        this.printWriter = client.getOutMsg();
        this.bufferedReader = client.getInMsg();

    }



    @Override
    public void run() {
        Thread.currentThread().setName("Client.BufferInputThread");
        String in;


//        System.out.println(Thread.currentThread().getName());
//        try {
//            String name = bufferedReader.readLine();
//            for (ClientsPair client : OnlineClients.getClientsPairList()) {
//                if (client.getPort() == socket.getPort()) {
//                    client.setName(name);
//                    username = name;
//                    myport = client.getPort();
//                }
//            }
//            printWriter.println("Chat: Welcome " + username + ". You're in chat now.");
//            System.out.println("Client with port " + myport + " - set name to: " + username);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Thread.currentThread().setName("BufferInput Thread from "+client.getName());
        boolean customBreak = false;
        while (!isInterrupted() && !customBreak) {
            try {
                in = bufferedReader.readLine();

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String currentTime = formatter.format(date);

                // send msg to all
                OnlineClients.getInstance().sendMsgAll(currentTime, client.getName(), in, client.getPort());
                System.out.println(currentTime+" "+client.getName()+" : "+in);


//                System.out.println(currentTime + " " + client.getName() + ": " + in);

                if (in.equals("exit")) {
                    OnlineClients.getInstance().setOffline(client.getName());
                    OnlineClients.getInstance().removeClient(client);
//                    bufferedReader.close();
//                    printWriter.close();
//                    client.getSocket().close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                customBreak = true;
            }

        }
//        String ClientLeftMessageForServer = "";
//        for (ClientsPair client : OnlineClients.getInstance().getClientsPairList()) {
//            if (client.getPort() == client.getSocket().getPort()) {
//                ClientLeftMessageForServer = client.getName() + " left the chat.";
//            }
//        }
//        System.out.println(ClientLeftMessageForServer);
    }
}
