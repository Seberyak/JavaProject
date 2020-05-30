package ChatRoom.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ThreadClient extends Thread {
    @Override
    public void run() {
        Socket socket;
        try {
            socket = new Socket("localhost", 4321);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(new InputStreamReader(System.in));

            BufferInputThread input = new BufferInputThread(bufferedReader);
            ScannerThread output = new ScannerThread(scanner, printWriter, input);
            input.start();
            output.start();

            try {
                input.join();
                output.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            System.out.println("Output thread: "+output.getState());
            System.out.println("________________________");
            System.out.println("Chat: You left the chat.");

            printWriter.close();
            scanner.close();
            bufferedReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
