package ChatRoom.Testing;

public class oneClass  extends  Thread{
    MyObj Logic;

    public oneClass(MyObj logic) {
        Logic = logic;
    }

    @Override
    public void run() {
        System.out.println(Logic);
        Logic.setMyObj(Logic.getMyObj()+1);
    }
}
