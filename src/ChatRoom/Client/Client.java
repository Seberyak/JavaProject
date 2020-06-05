package ChatRoom.Client;
/**
 * Client class starts ThreadClient class in a new thread.
 *
 * @author Daniel Barbakadze.
 * @author Valera Liparteliani.
 */
public class Client {
    public static void main(String[] args) {
        new ThreadClient().start();
    }
}
