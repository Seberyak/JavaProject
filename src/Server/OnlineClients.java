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
    public static void sendMsgAll(String currentTime,String username, String pureMsg, int port){
        String msg = currentTime + " " + username + ": " + pureMsg;
        if(pureMsg.equals("exit")){
            for(ClientsPair client:OnlineClients.getClientsPairList()){
                if(client.getPort()==port){
                    msg = "Chat: " + client.name + " left the chat.";
                }
            }
        }
        for(ClientsPair client:clientsPairList){
            if(client.getPort()!=port) client.getOutMsg().println(msg);
        }
    }


}
