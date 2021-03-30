package remindApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Todo extends Item {
    Date date;
    ArrayList<String> message = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public Todo(String stringDate) {
        try {
            this.date = formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void collectWord(String word) {
        message.add(word);
    }

    // todo fix this method, return all todo objects
    // Function checking matched date
    public boolean hasPreviousCurrentFollowingDate(Date searchDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(searchDate);
        calendar.add(Calendar.DATE, 1);
        Date followingDate = calendar.getTime();
        calendar.add(Calendar.DATE, -2);
        Date previousDate = calendar.getTime();
        boolean isMatched = false;
        if (date.equals(searchDate) || date.equals(previousDate) || date.equals(followingDate)) {
            isMatched = true;
        }
        return isMatched;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public void print() {
        String formattedDate = formatter.format(date);
        System.out.print("todo: " + formattedDate);
        // print the list of words to get the full sentence
        for (String word : message) {
            System.out.print(" " + word);
        }
        System.out.println();
    }
}
