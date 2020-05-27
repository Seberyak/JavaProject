package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class BufferInputThread extends Thread{
    BufferedReader bufferedReader;
    Socket socket;
    PrintWriter printWriter;

    public BufferInputThread(BufferedReader sc,Socket socket,PrintWriter printWriter) {
       this.bufferedReader = sc;
       this.socket = socket ;
       this.printWriter = printWriter;
    }


    @Override
    public void run() {
        Thread.currentThread().setName("Client.BufferInputThread");
        String in;
        String username = "temp";
//        System.out.println(Thread.currentThread().getName());
        try {
            String name = bufferedReader.readLine();
            for ( int i = 0; i<OnlineClients.getClientsPairList().size(); i++){
                if(OnlineClients.getClientsPairList().get(i).getPort()== socket.getPort()){
                    OnlineClients.getClientsPairList().get(i).setName(name);
                    username = name;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!isInterrupted()){
            try {
                in = bufferedReader.readLine();
                System.out.println("Sender " + username + ": " + in);
                if (in.equals("exit") ) {

                    bufferedReader.close();
                    printWriter.close();
                    socket.close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Inside Client.BufferInputThread: end");
    }
}
