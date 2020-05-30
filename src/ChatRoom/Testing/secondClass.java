package ChatRoom.Testing;

public class secondClass extends  Thread{
    MyObj Logic;

    public secondClass(MyObj logic) {
        Logic = logic;
    }

    @Override
    public void run() {
        System.out.println(Logic);
        Logic.setMyObj(Logic.getMyObj()+1);
    }
}