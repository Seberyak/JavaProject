package ChatRoom.Testing;

public class GlobalClass {

    public static void main(String[] args) throws InterruptedException {
        MyObj myObj = new MyObj(1);

        oneClass th1 = new oneClass(myObj);
        secondClass th2 = new secondClass(myObj);
        th1.start();
        th1.join();
        th2.start();
        th2.join();
        oneClass th3 = new oneClass(myObj);
        th3.start();

    }
}
