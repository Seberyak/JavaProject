package ChatRoom.Client;

import java.io.PrintWriter;
import java.util.Scanner;

public class ScannerThread extends Thread {

    PrintWriter out;
    BufferInputThread bufferInputThread;
    Scanner scanner;

    public ScannerThread(Scanner scanner, PrintWriter printWriter, BufferInputThread bufferInputThread) {
        this.scanner = scanner;
        this.out = printWriter;
        this.bufferInputThread = bufferInputThread;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("ChatRoom.Client.ScannerThread");
        String outMessage;

        while (!isInterrupted()) {
            try {
                outMessage = scanner.nextLine();
                if (!outMessage.isEmpty()) {
                    out.println(outMessage);
                }
                if (outMessage.equals("exit")) {
                    bufferInputThread.interrupt();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}