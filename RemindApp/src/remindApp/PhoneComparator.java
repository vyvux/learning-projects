package remindApp;

import java.util.Comparator;

public class PhoneComparator implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        String name1 = ((Phone) o1).getName();
        String name2 = ((Phone) o2).getName();
        return name1.compareTo(name2);
    }
}
