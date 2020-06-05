package ChatRoom.Client;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class ScannerThread is used to read input text from terminal,
 * send it to server and if it's necessary prints the exception.
 *
 * @author Daniel Barbakadze.
 * @author Valera Liparteliani.
 */
public class ScannerThread extends Thread {

    PrintWriter out;
    BufferInputThread bufferInputThread;
    Scanner scanner;

    /**
     * ScannerThread constructor gets scanner, printWriter and bufferInputThread.
     * scanner is used to read data from terminal.
     * printWriter is used to send some info to server.
     * bufferInputThread that is reference of current client's thread instance is used to
     * stop thread working if client writes "exit" in terminal.
     *
     * @param scanner is a input stream that gets values from terminal entered using keyboard.
     * @param printWriter is a stream to send info back to server.
     * @param bufferInputThread is a thread instance's reference.
     */
    public ScannerThread(Scanner scanner, PrintWriter printWriter, BufferInputThread bufferInputThread) {
        this.scanner = scanner;
        this.out = printWriter;
        this.bufferInputThread = bufferInputThread;
    }

    /**
     * Function run Overrides Thread.run(), that starts new thread.
     * This function works until is interrupted, it reads input
     * from terminal and sends it back to server until client enters exit.
     * In case of Exception it prints the exception but continues working.
     *
     */
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
