package Client;

public class Client {

    public static void main(String[] args) {

        ThreadClient client1 = new ThreadClient();
//        ThreadClient client2 = new ThreadClient();

        client1.start();
//        client2.start();

    }

}
