package remindApp;

import java.util.ArrayList;

public class Memo extends Item {

    // code for class
    String firstWord;

    // store words in a list, each word is an object of type String
    ArrayList<String> message = new ArrayList<>();

    public Memo(String firstWord) {
        this.firstWord = firstWord;
        message.add(firstWord);
    }

    public void collectWord(String word) {
        message.add(word);
    }


    @Override
    public void print() {
        System.out.print("memo:");
        // print the list of words to get the full sentence
        for (String word : message) {
            System.out.print(" " + word);
        }
        System.out.println();
    }
}
