package ChatRoom.UnitTests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ConnectionEndTest tests if connection ends as it's predicted,
 * also it checks if clients receive the messages sent from the
 * server and they also are able to see the messages sent from
 * each other to be ChatRoom fully proper working.
 *
 */
class ConnectionEndTest {
    @BeforeAll
    static void beforeTests() {
        System.out.println("Connection End Testing started");
    }

    @AfterAll
    static void afterTests() {
        System.out.println("Connection End Testing finished");
    }

    /**
     * ClientThread is imitation of real Client.
     * Instead of getting input from terminal test gets
     * predefined name and message as a variables, that is given
     * in constructor.
     *
     * printWriter sends this two variable to server. After that
     * printWriter send exit message from both client's thread.
     *
     * Actually, server is imitated here too and it's called
     * CustomBufferThreadInputExit as a Custom class that receives
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

                CustomBufferThreadInputExit input = new CustomBufferThreadInputExit(bufferedReader, this.name);

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
     * CustomBufferThreadInputExit is imitated Server class
     * that receives messages from imitated Client class,
     * process it as real Server does and checks if actions
     * is the same as it was predicted.
     *
     * In this particular test case it checks that message must be
     * predefined welcome/welcome back pattern or info that second client
     * left the chat, when received name equals first client's name.
     * To second client, client's name and welcome/welcome pattern
     * and first client's first message to be sure that these two
     * client receive messages and exit info from each other, also
     * to be sure that both of them receives messages from the server too.
     *
     */
    static class CustomBufferThreadInputExit extends Thread {
        BufferedReader bufferedReader;
        String name;
        int counter = 0;

        public CustomBufferThreadInputExit(BufferedReader sc, String name) {
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
                        assert in != null;
                        if (this.counter == 0) {
                            assertTrue(in.contains("Otari join the chat") || in.contains("Otari welcome back!"));
                        } else {
                            assertTrue(in.contains("Otari left the chat"));
                        }
                    }
                    if (this.name.equals("Otari")) {
                        if (this.counter == 0) {
                            assert in != null;
                            assertTrue(in.contains("Daniel join the chat") || in.contains("Daniel welcome back!"));
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
    void ServerTest2() {
        Thread thread3 = new Thread(new ClientThread("Daniel", "Gamarjoba"));
        thread3.start();
        Thread thread4 = new Thread(new ClientThread("Otari", "exit"));
        thread4.start();
    }
}
