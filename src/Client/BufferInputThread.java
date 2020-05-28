package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;


public class BufferInputThread extends Thread{
    BufferedReader bufferedReader;
    public BufferInputThread(BufferedReader sc) {
        this.bufferedReader = sc;
    }


    @Override
    public void run() {
        Thread.currentThread().setName("Client.BufferInputThread");
        String in;
        System.out.println(Thread.currentThread().getName());
        while (!isInterrupted()){
            try {
                in = bufferedReader.readLine();
                System.out.println(in);
//                if(in.equals("exit")) {
//                    System.out.println("Sender closed chat");
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Inside Client.BufferInputThread: end");


    }
}
