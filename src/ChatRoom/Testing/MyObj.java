package ChatRoom.Testing;

public class MyObj {
    Integer MyObj = null;

    public Integer getMyObj() {
        return MyObj;
    }

    public void setMyObj(Integer myObj) {
        MyObj = myObj;
    }

    public MyObj(Integer myObj) {
        MyObj = myObj;
    }

    @Override
    public String toString() {
        return "MyObj{" +
                "MyObj=" + MyObj +
                '}';
    }
}
