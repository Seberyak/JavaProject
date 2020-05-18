package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class BufferInputThread extends Thread{
    BufferedReader bufferedReader;
    BreakClass Break;
    PrintWriter printWriter;

    public BufferInputThread(BufferedReader sc,BreakClass aBreak,PrintWriter aprintWriter) {
        this.bufferedReader = sc;
        this.Break = aBreak;
        this.printWriter = aprintWriter;
    }


    @Override
    public void run() {
        Thread.currentThread().setName("Client.BufferInputThread");
        String in;
        System.out.println(Thread.currentThread().getName());
        while (true){
            try {
                in = bufferedReader.readLine();
                System.out.println("Sender : " + in);
                if (in.equals("exit") ) {Break.setBreak(true); printWriter.println("exit");}
                if(Break.getBreak()) break; //temp
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Inside Client.BufferInputThread: end");
//        try {
//            bufferedReader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
