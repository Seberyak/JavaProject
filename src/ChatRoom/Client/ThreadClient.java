package ChatRoom.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * ThreadClient class creates input and output connections and
 * after everything is done it closes everything that opened.
 *
 * @author Daniel Barbakadze.
 * @author Valera Liparteliani.
 */
public class ThreadClient extends Thread {

    /**
     * Function run Overrides Thread.run(), that starts new thread.
     * It creates socket object on port "4321" to connect Server.
     * bufferedReader reads incoming data from Server.
     * printWriter sends data to Server.
     * scanner gets input value from terminal.
     *
     * input is instance of BufferInputThread that creates new thread
     * to read incoming data from server using bufferedReader
     *
     * output is instance of BufferInputThread that creates new thread
     * to send data to server using printWriter
     *
     * Two different thread for input and output is necessary to prevent
     * collision during getting and sending synchronized data.
     *
     * Output thread is waiting to input thread to finish working to close itself.
     * In case of InterruptedException, exception will be printed, but Client stays awake.
     *
     * In the end everything closes to prevent infinitely/endless working threads.
     */
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
