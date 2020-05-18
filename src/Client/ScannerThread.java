package Client;

import java.io.PrintWriter;
import java.util.Scanner;

public class ScannerThread extends Thread{
    Scanner scanner;
    PrintWriter out;
    BreakClass Break;
    public ScannerThread(Scanner myObj, PrintWriter myObj2, BreakClass aBreak) {
        this.scanner = myObj;
        this.out = myObj2;
        this.Break = aBreak;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Client.ScannerThread");
        String outMessage;
        System.out.println(Thread.currentThread().getName());

        while (true){
            outMessage = scanner.nextLine();
            out.println(outMessage);
            if(outMessage.equals("exit")) Break.setBreak(true);
            if(Break.getBreak()) break;
        }

//    scanner.close();
    System.out.println("Inside Client.ScannerThread scanner closed");
//    out.close();


    }

}
