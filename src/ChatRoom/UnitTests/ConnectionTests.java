package ChatRoom.UnitTests;

import ChatRoom.Client.BufferInputThread;
import ChatRoom.Client.ScannerThread;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionTests {
    @BeforeAll
    static void beforeTests() {
        System.out.println("Connection Testing started");
    }

    @AfterAll
    static void afterTests() {
        System.out.println("Connection Testing finished");
    }

    class CustomBufferThreadInput extends Thread {
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
                            assertTrue(in.contains("Otari join the chat"));
                        } else {
                            assert in != null;
                            assertTrue(in.contains("Gagimarjos"));
                        }
                    }
                    if (this.name.equals("Otar")) {
                        if (this.counter == 0) {
                            assert in != null;
                            assertTrue(in.contains("Daniel join the chat"));
                        } else {
                            assert in != null;
                            assertTrue(in.contains("Gamarjoba"));
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    customBreak = true;
                }
            }
        }
    }

    class ClientThread implements Runnable {
        Socket socket;

        private String name;
        private String message;

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

    @Test
    void ServerTest1() {
        Thread thread1 = new Thread(new ClientThread("Daniel", "Gamarjoba"));
        thread1.start();
        Thread thread2 = new Thread(new ClientThread("Otari", "Gagimarjos"));
        thread2.start();
    }
}
