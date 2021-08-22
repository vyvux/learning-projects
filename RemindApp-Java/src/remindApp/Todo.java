package remindApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class Todo extends Item implements Comparable<Todo>{
    Date date;
    String message;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public Todo(String content){
        StringTokenizer info = new StringTokenizer(content);
        try {
            date = formatter.parse(info.nextToken());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        message = info.nextToken("");
    }

    public boolean hasPreviousCurrentFollowingDate(String theDate){
        Date searchDate = null;
        try {
            searchDate = formatter.parse(theDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean isMatched = false;

        if (searchDate != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(searchDate);

            calendar.add(Calendar.DATE, 1);
            Date followingDate = calendar.getTime();

            calendar.add(Calendar.DATE, -2);
            Date previousDate = calendar.getTime();


            if (date.equals(searchDate) || date.equals(previousDate) || date.equals(followingDate)) {
                isMatched = true;
            }
        }

        return isMatched;
    }

    @Override
    public void print() {
        String formattedDate = formatter.format(date);
        System.out.println("todo: " + formattedDate+ " "+ message);
    }

    @Override
    public int compareTo(Todo o) {
        return this.date.compareTo(o.date);
    }

}
