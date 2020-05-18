package Client;

import java.io.BufferedReader;
import java.io.IOException;


public class BufferInputThread extends Thread{
    BufferedReader bufferedReader;
    BreakClass Break;

    public BufferInputThread(BufferedReader sc,BreakClass aBreak) {
        this.bufferedReader = sc;
        this.Break = aBreak;
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
                if (in.equals("exit") ) Break.setBreak(true);
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
