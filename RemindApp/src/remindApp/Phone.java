package remindApp;

public class Phone extends Item {
    String name;
    String number;

    public Phone(String name) {
        this.name = name;
    }

    public void addPhoneNumber(String number) {
        this.number = number;
    }

    // Function checking string pattern for variable Name
    public boolean hasString(String stringValue) {
        int valueLength = stringValue.length();
        boolean hasString = false;
        if (name.length() >= stringValue.length()) {
            if (name.substring(0, valueLength).equals(stringValue)) hasString = true;
        }

        return hasString;
    }

    public String getName() {
        return name;
    }

    @Override
    public void print() {
        System.out.println("phone: " + name + " " + number);
    }
}
