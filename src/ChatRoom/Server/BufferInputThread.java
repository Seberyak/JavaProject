package ChatRoom.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BufferInputThread extends Thread {
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    ClientsPair client;

    /**
     * Variable client is used to get Class ClientsPair
     * instance and also get  printWriter and bufferedReader
     * from the same client's thread.
     *
     * @param client is Class ClientsPair instance.
     */
    public BufferInputThread(ClientsPair client) {
        this.client = client;
        this.printWriter = client.getOutMsg();
        this.bufferedReader = client.getInMsg();
    }

    /**
     * Function run Overrides Thread.run(), that starts new thread.
     * This function reads client's input, formats it and send it
     * to all online clients in ChatRoom, saves  this message in
     * DataBase and prints it in SocketServerThreaded terminal.
     *
     * If client enters exit this client's status turns into offline
     * and will be removed from online clients list.
     *
     */
    @Override
    public void run() {
        Thread.currentThread().setName("ChatRoom.Client.BufferInputThread");
        String in;

        Thread.currentThread().setName("BufferInput Thread from " + client.getName());
        boolean customBreak = false;
        while (!isInterrupted() && !customBreak) {
            try {
                in = bufferedReader.readLine();

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String currentTime = formatter.format(date);

                // send msg to all
                OnlineClients.getInstance().sendMsgAll(currentTime, client.getName(), in, client.getPort());
                //send to db
//                SocketServerThreaded.conn.insertExample(client.getName(),in,new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                //print on server
                System.out.println(currentTime + " " + client.getName() + " : " + in);

                if (in.equals("exit")) {
                    OnlineClients.getInstance().setOffline(client.getName());
                    OnlineClients.getInstance().removeClient(client);
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                customBreak = true;
            }
        }
    }
}
