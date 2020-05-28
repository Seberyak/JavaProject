package Server;

import java.util.ArrayList;

public class OnlineClients {
    public static ArrayList<ClientsPair> clientsPairList = new ArrayList<>();

    public static void setClientsPairList(ClientsPair clientsPairList) {
        OnlineClients.clientsPairList.add(clientsPairList);
    }

    public static ArrayList<ClientsPair> getClientsPairList() {
        return clientsPairList;
    }
    public static void sendMsgAll(String msg, int port){
        for(ClientsPair client:clientsPairList){
            if(client.getPort()!=port) client.getOutMsg().println(msg);
        }
    }


}
