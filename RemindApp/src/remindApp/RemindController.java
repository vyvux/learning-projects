package remindApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RemindController {
    // Declaration that creates storage for the Items
    private ArrayList<Item> listOfItems = new ArrayList<>();

    /**
     * Function printing out all items in the database
     */
    public void printList() {
        if (listOfItems.isEmpty()) {
            System.out.println("Nothing recorded");
        } else {
            for (Item item : listOfItems) {
                item.print();
            }
        }
    }

    /**
     * Function creating a Memo item
     */
    public void createMemo(StringTokenizer line) {
        // create new Memo object and save the first word in the array of Memo message
        Memo newMemo = new Memo(line.nextToken());
        // save following words in the array of Memo message
        while (line.hasMoreTokens()) {
            newMemo.collectWord(line.nextToken());
        }
        // add memo in the database
        listOfItems.add(newMemo);
    }

    /**
     * Function creating a Phone item
     */
    public void createPhone(StringTokenizer line) {
        Phone newPhone = new Phone(line.nextToken()); // read in the name
        newPhone.addPhoneNumber(line.nextToken()); // read in the number
        listOfItems.add(newPhone);
    }

    /**
     * Function creating a To-do item
     */
    public void createTodo(StringTokenizer line) {
        Todo newTodo = new Todo(line.nextToken()); // read in the date
        while (line.hasMoreTokens()) {
            newTodo.collectWord(line.nextToken());
        }
        listOfItems.add(newTodo);
    }

    /**
     * Function printing only list of Memo objects
     */
    public void printOnlyMemoItems() {
        Collection<Item> listOfMemoObjects = findOnlyMemoInTheList(listOfItems);
        printAList(listOfMemoObjects);
    }

    /**
     * Method filtering only Memo items
     *
     * @return a collection of type Item with only Memo objects
     */
    public Collection<Item> findOnlyMemoInTheList(Collection<Item> theList) {
        Collection<Item> memoObjectsList = new ArrayList<>();
        for (Item item : theList) {
            if (item.getClass() == Memo.class) {
                memoObjectsList.add(item);
            }
        }
        return memoObjectsList;
    }


    /**
     * Function printing only list of Phone objects
     */
    public void printOnlyPhoneItems() {
        Collection<Item> listOfPhoneObjects = findOnlyPhoneInTheList(listOfItems);
        printAList(listOfPhoneObjects);
    }

    /**
     * Method filtering only Phone items
     *
     * @return a collection of type Item with only Phone objects
     */
    public Collection<Item> findOnlyPhoneInTheList(Collection<Item> theList) {
        Collection<Item> phoneObjectsList = new ArrayList<>();
        for (Item item : theList) {
            if (item.getClass() == Phone.class) {
                phoneObjectsList.add(item);
            }
        }
        return phoneObjectsList;
    }


    /**
     * Function printing only list of To-do objects
     */
    public void printOnlyTodoItems() {
        Collection<Item> listOfTodoObjects = findOnlyTodoInTheList(listOfItems);
        printAList(listOfTodoObjects);
    }

    /**
     * Method filtering only To-do items
     *
     * @return a collection of type Item with only To-do objects
     */
    public Collection<Item> findOnlyTodoInTheList(Collection<Item> theList) {
        Collection<Item> todoObjectsList = new ArrayList<>();
        for (Item item : theList) {
            if (item.getClass() == Todo.class) {
                todoObjectsList.add(item);
            }
        }
        return todoObjectsList;
    }


    /**
     * Function handling phone command with one argument
     */
    public void phoneCommandHandler(StringTokenizer line) {
        String argument = line.nextToken();
        if (argument.equals("-a")) {
            // display a list of phone items in alphabetic order
            printAList(sortPhoneAlphabetically());
        } else {
            //behave as before: search phone item by name
            printAList(findPhoneInTheListHavingMatchedName(listOfItems, argument));
        }
    }

    /**
     * Method searching for Phone items having their Names match with an input String value
     *
     * @return a collection of type Item having only Phone objects with matched name
     */
    public Collection<Item> findPhoneInTheListHavingMatchedName(Collection<Item> theList, String stringPattern) {
        // firstly, find the list of only Phone objects
        Collection<Item> phoneObjectList = findOnlyPhoneInTheList(theList);
        // create a list to store string matched Phone objects
        Collection<Item> listOfPhoneWithMatchedName = new ArrayList<>();
        for (Item item : phoneObjectList) {
            //cast Item objects to Phone objects -> use method hasString in Class Phone
            if (((Phone) item).hasString(stringPattern)) listOfPhoneWithMatchedName.add(item);
        }
        return listOfPhoneWithMatchedName;
    }

    /**
     * Method sorting Phone items
     *
     * @return a collection of type Item having only Phone objects in alphabetical order
     */
    public Collection<Item> sortPhoneAlphabetically() {
        // return the list of phone items before sorting
        ArrayList<Item> listOfPhoneObjects = (ArrayList<Item>) findOnlyPhoneInTheList(listOfItems);
        // sort the list by implementing PhoneComparator class
        Collections.sort(listOfPhoneObjects, new PhoneComparator());
        return listOfPhoneObjects;
    }


    /**
     * Function handling to-do command with one argument
     */
    public void todoCommandHandler(StringTokenizer line) {
        String argument = line.nextToken();
        // display a list of to-do items sorted by date
        if (argument.equals("-d")) {
            printAList(sortTodoByDate());
        }
        // behave as before: search to-do falling in specific date
        else {
            //convert the date to a Date object
            Date searchDate = covertStringToDate(argument);
            printAList(findTodoInTheListHavingMatchedDate(listOfItems, searchDate));
        }
    }

    /**
     * Method searching for To-do items having their date fall in search date, previous or following date of search date
     *
     * @return a collection of type Item having only To-do objects with matched date
     */
    public Collection<Item> findTodoInTheListHavingMatchedDate(Collection<Item> theList, Date searchDate) {
        // firstly, find the list of only Phone objects
        Collection<Item> todoObjectList = findOnlyTodoInTheList(theList);
        // create a list to store string matched Phone objects
        Collection<Item> listOfTodoObjectsMatchedDate = new ArrayList<>();
        for (Item item : todoObjectList) {
            //cast Item objects to Phone objects -> use method hasString in Class Phone
            if (((Todo) item).hasPreviousCurrentFollowingDate(searchDate)) {
                listOfTodoObjectsMatchedDate.add(item);
            }
        }
        return listOfTodoObjectsMatchedDate;
    }

    /**
     * Method sorting To-do items
     *
     * @return a collection of type Item having only To-do objects sorted by date
     */
    public Collection<Item> sortTodoByDate() {
        // return the list of to-do items before sorting
        ArrayList<Item> listOfTodoSortedByDate = (ArrayList<Item>) findOnlyTodoInTheList(listOfItems);
        // sort the list by implementing DateComparator class
        Collections.sort(listOfTodoSortedByDate, new DateComparator());
        return listOfTodoSortedByDate;
    }

    /**
     * Method converting string to date object
     */
    public Date covertStringToDate(String stringDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date formattedDate = null;
        try {
            formattedDate = formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }


    /**
     * Method printing out a list of a certain item type
     */
    public void printAList(Collection<Item> theList) {
        if (theList.isEmpty()) {
            System.out.println("Nothing recorded");
        } else {
            for (Item item : theList) {
                item.print();
            }
        }
    }
}
