package remindApp;

public class Phone extends Item implements Comparable<Phone>{
    String name;
    String number;

    public Phone(String content){
        String[] contact = content.split(" ");
        name = contact[0];
        number = contact[1];
    }
    @Override
    public void print() {
        System.out.println("phone: "+ name +" "+ number);
    }

    @Override
    public int compareTo(Phone o) {
        return this.name.compareTo(o.name);
    }
}
