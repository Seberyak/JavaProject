package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadClient {

//    public static boolean Exit = false;



    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 4321);
        BreakClass Break = new BreakClass();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
        Scanner scanner = new Scanner(System.in);

        BufferInputThread input = new BufferInputThread(bufferedReader,Break);
        ScannerThread output = new ScannerThread(scanner,printWriter,Break);

        input.start();
        output.start();

//        while (true){
//            if(!input.isAlive()){output.interrupt();break;  }
//        }
        output.join();
        System.out.println("Thread Client: output end."+output.getState());
        input.join();
        System.out.println("Thread client: input end"+input.getState());
        printWriter.close();
        scanner.close();
        bufferedReader.close();
        socket.close();

    }

}
