package ChatRoom.Server;

import java.util.ArrayList;

/**
 * Singleton Class OnlineClients is used as
 * a global variable store online and offline
 * clients. It also have methods to Add Client
 * to list, Change client's status and message
 * to all the clients.
 *
 * @author Daniel Barbakadze.
 * @author Valera Liparteliani.
 */
public class OnlineClients {

    public ArrayList<ClientsPair> clientsPairList = new ArrayList<>();
    private ArrayList<String> offlineClients = new ArrayList<>(); //here is clients names with offline status

    /**
     * clientsPairList is singleton that is used as a global variable
     * to store offline and online Clients information.
     *
     * ArrayList clientsPairList stores Online users.
     * ArrayList offlineClients stores Offline users.
     *
     */
    private OnlineClients() {
    }

    private static final OnlineClients INSTANCE = new OnlineClients();

    /**
     * Gets self instance.
     *
     * @return INSTANCE returns self instance.
     */
    public static OnlineClients getInstance() {
        return INSTANCE;
    }

    /**
     * Method addClientToList checks new client's name
     * exists or not. If it exists user gets new message
     * that this name is already taken an give s/he chance
     * to try again choosing a name.
     *
     * If user already exists in ClientList s/he will receive
     * welcome back message and status for this client turns
     * into online.
     *
     * @param newClient new Client instance.
     * @return false if user exist, otherwise returns true.
     */
    public boolean addClientToList(ClientsPair newClient) {
        for (ClientsPair client : clientsPairList) {

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
        return true;

    }

    /**
     * Gets online clients list.
     *
     * @return clientsPairList online clients list.
     */
    public ArrayList<ClientsPair> getClientsPairList() {
        return clientsPairList;
    }

    /**
     * Method sendMsgAll formats a new message received from
     * client using given information and send it to all online
     * clients.
     *
     * If client enters a word `exit` other online clients get
     * message that this user left the chat. The same message is
     * printed on server side terminal.
     *
     * @param currentTime the time client sent the message.
     * @param username client's name.
     * @param pureMsg client's message.
     * @param port client's port.
     */
    public void sendMsgAll(String currentTime, String username, String pureMsg, int port) {
        String msg = currentTime + " " + username + ": " + pureMsg;
        String name = "";
        if (pureMsg.equals("exit")) {
            for (ClientsPair client : clientsPairList) {
                if (client.getPort() == port) {
                    msg = "Chat: " + client.getName() + " left the chat.";
                    name = client.getName();
                }
            }
        }
        if (!name.isEmpty()) System.out.println(name + " left the chat.");
        for (ClientsPair client : clientsPairList) {
            if (client.getPort() != port) client.getOutMsg().println(msg);
        }
    }

    /**
     * Adds client in offline Clients list.
     *
     * @param username Client's name.
     */
    public void setOffline(String username) {
        offlineClients.add(username);
    }

    /**
     * Gets clients from offline Clients list.
     *
     * @return offlineClients offline clients list.
     */
    public ArrayList<String> getOfflineClients() {
        return offlineClients;
    }

    /**
     * Removes client's object from the Clients Pair List.
     *
     * @param client all info about client.
     */
    public void removeClient(ClientsPair client) {
        clientsPairList.remove(client);
    }
}
