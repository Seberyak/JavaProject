package Sever;

import java.io.PrintWriter;
import java.util.Scanner;

public class ScannerThread extends Thread {
    Scanner scanner;
    PrintWriter out;

    public ScannerThread(Scanner myObj, PrintWriter myObj2) {
        this.scanner = myObj;
        this.out = myObj2;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("ChatRoom.Client.ScannerThread");
        String outMessage;
        System.out.println(Thread.currentThread().getName());
        while (true) {
            outMessage = scanner.nextLine();
            out.println(outMessage);
            if (outMessage.equals("exit")) {
                break;
            }
//                System.out.println("System input : "+in);
        }
//    out.close();
//    System.out.println("Inside ChatRoom.Client.ScannerThread: out closed ");
        scanner.close();
        System.out.println("Inside ChatRoom.Server.ScannerThread scanner closed");
//    out.close();
    }

}
