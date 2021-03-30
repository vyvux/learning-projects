package remindApp;

import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<Item> {
    @Override
    public int compare(Item o1, Item o2) {
        Date date1 = ((Todo) o1).getDate();
        Date date2 = ((Todo) o2).getDate();
        return date1.compareTo(date2);
    }
}
