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

class ConnectionTests {
    @BeforeAll
    static void beforeTests() {
        System.out.println("Connection Testing started");
    }

    @AfterAll
    static void afterTests() {
        System.out.println("Connection Testing finished");
    }

    class ClientThread implements Runnable {
        Socket socket;

        private final String name;
        private final String message;
        private final int testIndex;

        public ClientThread(String name, String message, int testIndex) {
            this.name = name;
            this.message = message;
            this.testIndex = testIndex;
        }

        @Override
        public void run() {
            try {
                socket = new Socket("localhost", 4321);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

                printWriter.println(this.name);
                printWriter.println(this.message);

                Thread input = new Thread();
                if(this.testIndex==1){
                    input = new CustomBufferThreadInput(bufferedReader, this.name);
                }
                else if(this.testIndex==2){
                    input = new CustomBufferThreadInputExit(bufferedReader, this.name);
                }

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
                    if (this.name.equals("Otari")) {
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

    class CustomBufferThreadInputExit extends Thread {
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
                        if (this.counter == 0) {
                            assert in != null;
                            assertTrue(in.contains("Otari join the chat") || in.contains("Otari welcome back!"));
                        } else {
                            assert in != null;
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

    @Test
    void ServerTest1() {
        Thread thread1 = new Thread(new ClientThread("Daniel", "Gamarjoba",1));
        thread1.start();
        Thread thread2 = new Thread(new ClientThread("Otari", "Gagimarjos",1));
        thread2.start();
    }

    @Test
    void ServerTest2() {
        Thread thread1 = new Thread(new ClientThread("Daniel", "Gamarjoba",2));
        thread1.start();
        Thread thread2 = new Thread(new ClientThread("Otari", "exit",2));
        thread2.start();
    }
}
