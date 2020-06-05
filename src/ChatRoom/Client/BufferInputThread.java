package ChatRoom.Client;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * BufferInputThread gets bufferReader and prints incoming
 * messages in Client's terminal using a new thread.
 *
 * @author Daniel Barbakadze.
 * @author Valera Liparteliani.
 *
 */
public class BufferInputThread extends Thread {

    BufferedReader bufferedReader;

    /**
     * BufferInputThread constructor sets bufferReader param.
     *
     * @param bufferReader is used to represent instance of
     * BufferedReader that reads the message.
     */
    public BufferInputThread(BufferedReader bufferReader) {
        this.bufferedReader = bufferReader;
    }

    /**
     * Function run Overrides Thread.run(), that starts new thread.
     *
     * This function is used to read incoming messages and print it
     * in Client's terminal. It works while this thread is
     * interrupted or IOException appears.
     */
    @Override
    public void run() {
        Thread.currentThread().setName("ChatRoom.Client.BufferInputThread");
        String in;
        System.out.println("Welcome! You've joined to our chat.");

        boolean customBreak = false;
        while (!isInterrupted() && !customBreak) {
            try {
                in = bufferedReader.readLine();
                if (in != null) {
                    System.out.println(in);
                }
            } catch (IOException e) {
                e.printStackTrace();
                customBreak = true;
            }
        }
    }
}
