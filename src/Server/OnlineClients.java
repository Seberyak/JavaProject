package Server;

import java.util.ArrayList;

public class OnlineClients {
    public static ArrayList<ClientsPair> clientsPairList = new ArrayList<>();

    public static boolean addClientToList(ClientsPair newClient) {
        if(!getClientsPairList().isEmpty()){

            for(ClientsPair client:clientsPairList){

                //if client with same name is online, new client must enter another name...
                if(newClient.getName().equals(client.getName()) && client.isOnline()) {
                    newClient.getOutMsg().println("The user with that name already exists!");
                    return false;
                }
                else if(newClient.getName().equals(client.getName()) && !client.isOnline()){

//                    OnlineClients.removeClient(newClient.getName());
                    newClient.getOutMsg().println("Welcome back, "+ client.getName()+'!');
                    OnlineClients.getClientsPairList().remove(client);
                }
            }
        }
        OnlineClients.clientsPairList.add(newClient);

        return true;
    }

    public static ArrayList<ClientsPair> getClientsPairList() {
        return clientsPairList;
    }
    public static void sendMsgAll(String currentTime,String username, String pureMsg, int port){
        String msg = currentTime + " " + username + ": " + pureMsg;
        if(pureMsg.equals("exit")){
            for(ClientsPair client:clientsPairList){
                if(client.getPort()==port){
                    msg = "Chat: " + client.getName() + " left the chat.";
                }
            }
        }
        for(ClientsPair client:clientsPairList){
            if(client.getPort()!=port) client.getOutMsg().println(msg);
        }
    }
    public static void removeClient(String username){

        for (ClientsPair client:clientsPairList){
            if(username.equals(client.getName())) {
                client.setOnlineStatus(false);
                break;
            }
        }

    }


}
