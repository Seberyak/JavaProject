package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
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
        System.out.println(Thread.currentThread().getName());
        while (!isInterrupted()){
            try {
                in = bufferedReader.readLine();
                System.out.println("Sender : " + in);
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
