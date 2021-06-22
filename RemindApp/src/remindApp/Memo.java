package remindApp;

public class Memo extends Item {
    String message;

    public Memo(String memo){
        message = memo;
    }

    @Override
    public void print() {
        System.out.println("memo: "+message);
    }
}
