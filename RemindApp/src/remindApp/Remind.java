package remindApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Remind {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String command;

        // Declaration that creates storage for the Memos (Items)
        ArrayList<Item> listOfItems = new ArrayList<>();


        do {
            System.out.print("\nCommand? ");
            StringTokenizer line = new StringTokenizer(input.nextLine());
            command = line.nextToken().toLowerCase();
            switch (command) {
                case "exit" -> {
                }

                case "list" ->
                        /* code for the "list" command goes here;*/
                        //printList() static method of the Remind class
                        printList(listOfItems);

                case "memo" -> {
                    /* code for the "memo" command goes here */
                    //command with no args, display a list of just Memo
                    if (!line.hasMoreTokens()) {
                        Collection<Item> listOfMemoObjects = Remind.findOnlyMemoInTheList(listOfItems);
                        printList(listOfMemoObjects);
                    }
                    // record input as new memo object and add to the Item objects list
                    else {
                        Memo newMemo = new Memo(line.nextToken());
                        while (line.hasMoreTokens()) {
                            newMemo.collectWord(line.nextToken());
                        }
                        listOfItems.add(newMemo);
                    }
                }

                case "phone" -> {
                    // count the number of arguments
                    int argCounter = line.countTokens();
                    switch (argCounter) {
                        //no arguments, display a list of all Phone objects
                        case 0 -> {
                            Collection<Item> listOfPhoneObjects = findOnlyPhoneInTheList(listOfItems);
                            printList(listOfPhoneObjects);
                        }
                        // one argument, display a list of the Phone objects of names that start with the string value
                        case 1 -> {
                            String argument = line.nextToken();
                            // display a list of phone items in alphabetic order
                            if (argument.equals("-a")) {
                                // return the list of phone items before sorting
                                ArrayList<Item> listOfPhoneObjects = (ArrayList<Item>) findOnlyPhoneInTheList(listOfItems);
                                // sort the list by implementing PhoneComparator class
                                Collections.sort(listOfPhoneObjects, new PhoneComparator());
                                printList(listOfPhoneObjects);
                            }
                            //behave as before: search phone item by name
                            else {
                                // String searchTerm = line.nextToken();
                                Collection<Item> listOfPhoneWithMatchedName = findPhoneInTheListHavingMatchedName(listOfItems, argument);
                                printList(listOfPhoneWithMatchedName);
                            }
                        }
                        // two arguments, record input as new Phone object and add to the Item object list
                        case 2 -> {
                            Phone newPhone = new Phone(line.nextToken()); // read in the name
                            newPhone.addPhoneNumber(line.nextToken()); // read in the number
                            listOfItems.add(newPhone);
                        }
                    } //end switch
                }

                case "todo" -> {
                    // count the number of arguments
                    int argCounter = line.countTokens();
                    switch (argCounter) {
                        //no arguments, display a list of all Phone objects
                        case 0 -> {
                            Collection<Item> listOfTodoObjects = findOnlyTodoInTheList(listOfItems);
                            printList(listOfTodoObjects);
                        }
                        // one argument, display a list of the Phone objects of names that start with the string value
                        case 1 -> {
                            String argument = line.nextToken();
                            // display a list of to-do items in alphabetic order
                            if (argument.equals("-d")) {
                                // return the list of to-do items before sorting
                                ArrayList<Item> listOfTodoObjects = (ArrayList<Item>) findOnlyTodoInTheList(listOfItems);
                                // sort the list by implementing DateComparator class
                                Collections.sort(listOfTodoObjects, new DateComparator());
                                printList(listOfTodoObjects);
                            }
                            // behave as before: search to-do by date
                            else {
                                Date searchDate = covertStringToDate(argument);
                                Collection<Item> listOfTodoWithMatchedDate = findTodoInTheListHavingMatchedDate(listOfItems, searchDate);
                                printList(listOfTodoWithMatchedDate);
                            }
                        }
                        // two arguments, record input as new t-odo object and add to the Item object list
                        default -> {
                            Todo newTodo = new Todo(line.nextToken()); // read in the date
                            while (line.hasMoreTokens()) {
                                newTodo.collectWord(line.nextToken());
                            }
                            listOfItems.add(newTodo);
                        }
                    }
                }

                default -> System.out.println("Can't understand command " + command);
            }
        } while (!command.equals("exit"));
    }  // end Main

    /**
     * Function retrieving list of Phone objects having their Names match with the input date
     */
    public static Collection<Item> findTodoInTheListHavingMatchedDate(Collection<Item> theList, Date searchDate) {
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
     * Function converting string to date object
     */
    public static Date covertStringToDate(String stringDate) {
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
     * Function retrieving only list of T-odo objects
     */
    public static Collection<Item> findOnlyTodoInTheList(Collection<Item> theList) {
        Collection<Item> todoObjectsList = new ArrayList<>();
        for (Item item : theList) {
            if (item.getClass() == Todo.class) {
                todoObjectsList.add(item);
            }
        }
        return todoObjectsList;
    }

    /**
     * Function retrieving list of Phone objects having their Names match with an input String value
     */
    public static Collection<Item> findPhoneInTheListHavingMatchedName(Collection<Item> theList, String stringPattern) {
        // firstly, find the list of only Phone objects
        Collection<Item> phoneObjectList = findOnlyPhoneInTheList(theList);
        // create a list to store string matched Phone objects
        Collection<Item> listOfPhoneObjectsHasString = new ArrayList<>();
        for (Item item : phoneObjectList) {
            //cast Item objects to Phone objects -> use method hasString in Class Phone
            if (((Phone) item).hasString(stringPattern)) listOfPhoneObjectsHasString.add(item);
        }
        return listOfPhoneObjectsHasString;
    }

    /**
     * Function retrieving only list of Phone objects
     */
    public static Collection<Item> findOnlyPhoneInTheList(Collection<Item> theList) {
        Collection<Item> phoneObjectsList = new ArrayList<>();
        for (Item item : theList) {
            if (item.getClass() == Phone.class) {
                phoneObjectsList.add(item);
            }
        }
        return phoneObjectsList;
    }

    /**
     * Function retrieving only list of Phone objects
     */
    public static Collection<Item> findOnlyMemoInTheList(Collection<Item> theList) {
        Collection<Item> memoObjectsList = new ArrayList<>();
        for (Item item : theList) {
            if (item.getClass() == Memo.class) {
                memoObjectsList.add(item);
            }
        }
        return memoObjectsList;
    }

    /**
     * Function printing out a list
     */
    public static void printList(Collection<Item> theList) {
        if (theList.isEmpty()) {
            System.out.println("Nothing recorded");
        } else {
            for (Item item : theList) {
                item.print();
            }
        }
    }


}
