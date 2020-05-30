package ChatRoom.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class SocketThread implements Runnable {

    Socket socket;

    public SocketThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String message = in.readLine();

                if (message.equalsIgnoreCase("hello")) {
                    out.println("Hi ChatRoom.Client");
                } else if (message.equalsIgnoreCase("exit")) {
                    out.println("Bye ChatRoom.Client, server shutting down!");
                    break;
                } else {
                    out.println(message);
                }
            }
            System.out.println(Thread.currentThread().getName() + " Shutting Socket thread!");

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}