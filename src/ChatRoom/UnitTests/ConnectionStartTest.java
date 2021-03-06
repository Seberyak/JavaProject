package ChatRoom.UnitTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ConnectionStartTest tests if connection starts as it's predicted,
 * also it checks if clients receive the messages sent from the
 * server and they also are able to see the messages sent from
 * each other to be ChatRoom fully proper working.
 *
 */
class ConnectionStartTest {
    @BeforeAll
    static void beforeTests() {
        System.out.println("Connection Start Testing started");
    }

    @AfterAll
    static void afterTests() {
        System.out.println("Connection Start Testing finished");
    }

    /**
     * ClientThread is imitation of real Client.
     * Instead of getting input from terminal test gets
     * predefined name and message as a variables, that is given
     * in constructor.
     *
     * printWriter sends this two variable to server.
     * printWriter send exit message from both client's thread.
     *
     * Actually, server is imitated here too and it's called
     * CustomBufferThreadInput as a Custom class that receives
     * client's name and message and process it.
     *
     */
    static class ClientThread implements Runnable {
        Socket socket;

        private final String name;
        private final String message;

        public ClientThread(String name, String message) {
            this.name = name;
            this.message = message;
        }

        @Override
        public void run() {
            try {
                socket = new Socket("localhost", 4321);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

                printWriter.println(this.name);
                printWriter.println(this.message);

                CustomBufferThreadInput input = new CustomBufferThreadInput(bufferedReader, this.name);

                printWriter.println("exit");

                input.start();
                try {
                    input.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                printWriter.close();
                bufferedReader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * CustomBufferThreadInput is imitated Server class
     * that receives messages from imitated Client class,
     * process it as real Server does and checks if actions
     * is the same as it was predicted.
     *
     * In this particular test case it checks if when received
     * name equals Daniel message must be predefined welcome
     * pattern or the message that second client send. The same
     * for the second client, It's name and welcome pattern
     * text and first client's first message to be sure that
     * these two client receive messages from each other and
     * from the server too.
     *
     */
    static class CustomBufferThreadInput extends Thread {
        BufferedReader bufferedReader;
        String name;
        int counter = 0;

        public CustomBufferThreadInput(BufferedReader sc, String name) {
            this.bufferedReader = sc;
            this.name = name;
        }

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

                    if (this.name.equals("Daniel")) {
                        if (this.counter == 0) {
                            assert in != null;
                            assertTrue(in.contains("Daniel join the chat"));
                        } else {
                            assert in != null;
                            assertTrue(in.contains("Gagimarjos"));
                        }
                    }
                    if (this.name.equals("Otari")) {
                        if (this.counter == 0) {
                            assert in != null;
                            assertTrue(in.contains("Otari join the chat"));
                        } else {
                            assert in != null;
                            assertTrue(in.contains("Gamarjoba"));
                        }
                    }
                    this.counter = this.counter + 1;

                } catch (IOException e) {
                    e.printStackTrace();
                    customBreak = true;
                }
            }
        }
    }

    /**
     * This test creates predefined two imitated Client class instance
     * in a new thread and pass it ClientThread instance that has
     * passed name and the first message to check if everything
     * works as it was predicted.
     *
     */
    @Test
    void ServerTest1() {
        Thread thread1 = new Thread(new ClientThread("Daniel", "Gamarjoba"));
        thread1.start();
        Thread thread2 = new Thread(new ClientThread("Otari", "Gagimarjos"));
        thread2.start();
    }

}
