package Server;

import java.util.ArrayList;

public class OnlineClients {
    public  ArrayList<ClientsPair> clientsPairList = new ArrayList<>();
    private   ArrayList<String> offlineClients= new ArrayList<>(); //here is clients names with offline status

    private OnlineClients(){}

    private static  final OnlineClients INSTANCE = new OnlineClients();

    public static OnlineClients getInstance() {
        return INSTANCE;
    }



    public boolean addClientToList(ClientsPair newClient) {
        for (ClientsPair client: clientsPairList) {

            //if client with same name is online, new client must enter another name...
            if (client.getName().equals(newClient.getName())) {
                newClient.getOutMsg().println("The user with that name already exists!");
                return false;
            }
        }
        for (String offlineclient : offlineClients) {
            if (offlineclient.equals(newClient.getName())) {
                newClient.getOutMsg().println(newClient.getName() + " welcome back!");
                clientsPairList.add(newClient);
                //changed!
                offlineClients.remove(offlineclient);
                return true;
            }
        }

        this.clientsPairList.add(newClient);
        return  true;

    }

    public  ArrayList<ClientsPair> getClientsPairList() {
        return clientsPairList;
    }

    public  void sendMsgAll(String currentTime,String username, String pureMsg, int port){
        String msg = currentTime + " " + username + ": " + pureMsg;
        String name="";
        if(pureMsg.equals("exit")){
            for(ClientsPair client:clientsPairList){
                if(client.getPort()==port){
                    msg = "Chat: " + client.getName() + " left the chat.";
                    name = client.getName();
                }
            }
        }
        if(!name.isEmpty()) System.out.println(name+ " left the chat.");
        for(ClientsPair client:clientsPairList){
            if(client.getPort()!=port) client.getOutMsg().println(msg);
        }
    }


    public  void setOffline(String username){
        offlineClients.add(username);
    }

    public  ArrayList<String> getOfflineClients() {
        return offlineClients;
    }

    public  void removeClient(ClientsPair client){
        clientsPairList.remove(client);
    }


}
