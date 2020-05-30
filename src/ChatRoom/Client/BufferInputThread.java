package ChatRoom.Client;

import java.io.BufferedReader;
import java.io.IOException;

public class BufferInputThread extends Thread {
    BufferedReader bufferedReader;

    public BufferInputThread(BufferedReader sc) {
        this.bufferedReader = sc;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("ChatRoom.Client.BufferInputThread");
        String in;
//        System.out.println(Thread.currentThread().getName());
        System.out.println("Welcome! You've joined to our chat.");
        boolean customBreak = false;
        while (!isInterrupted() && !customBreak) {
            try {
                in = bufferedReader.readLine();
                if (in != null) {
                    System.out.println(in);
                }
//                if(in.equals("exit")) {
//                    System.out.println("Sender closed chat");
//                }
            } catch (IOException e) {
                e.printStackTrace();
                customBreak = true;
            }
        }
    }
}
