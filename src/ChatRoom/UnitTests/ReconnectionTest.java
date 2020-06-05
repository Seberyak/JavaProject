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

class ReconnectionTest {
    @BeforeAll
    static void beforeTests() {
        System.out.println("Reconnection Testing started");
    }

    @AfterAll
    static void afterTests() {
        System.out.println("Reconnection Testing finished");
    }

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

//                printWriter.println("exit");

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
                            assertTrue(in.contains("Daniel Join the chat")
                                    || in.contains("The user with that name already exists!")
                            );
                        } else {
                            assert in != null;
                        }
                    }
                    this.counter = this.counter + 1;
//                    System.out.println((this.counter));
                } catch (IOException e) {
                    e.printStackTrace();
                    customBreak = true;
                }
            }
        }
    }

    @Test
    void ServerTest1() {
        Thread thread1 = new Thread(new ClientThread("Daniel", "Daniel"));
        thread1.start();
        Thread thread2 = new Thread(new ClientThread("Daniel", "Daniel"));
        thread2.start();
    }

}
