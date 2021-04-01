package remindApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Remind {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String command;

        // instantiate a controller object
        RemindController controller = new RemindController();

        do {
            System.out.print("\nCommand? ");
            StringTokenizer line = new StringTokenizer(input.nextLine());
            command = line.nextToken().toLowerCase();
            switch (command) {
                case "exit" -> {
                }

                case "list" -> controller.printList();

                case "memo" -> {
                    //command with no args, display a list of just Memo
                    if (!line.hasMoreTokens()) {
                        controller.printOnlyMemoItems();
                    }
                    // record input as new memo object and add to the Item objects list
                    else {
                        controller.createMemo(line);
                    }
                }
                case "phone" -> {
                    //variable storing the number of arguments
                    int argCounter = line.countTokens();
                    switch (argCounter) {
                        //no arguments, display a list of all Phone objects
                        case 0 -> controller.printOnlyPhoneItems();

                        // one argument
                        // display list of the Phone objects having name start with a string pattern
                        // OR display list of Phone items sorted by name
                        case 1 -> controller.phoneCommandHandler(line);

                        // two arguments, record input as new Phone object
                        case 2 -> controller.createPhone(line);

                    } //end switch
                }
                case "todo" -> {
                    // variable storing the number of arguments
                    int argCounter = line.countTokens();
                    switch (argCounter) {
                        //no arguments, display a list of all To-do objects
                        case 0 -> controller.printOnlyTodoItems();

                        // one argument,
                        // display list of the To-do objects having date fall in search date or previous and following date of the search date
                        // OR display list of To-do objects sorted by date
                        case 1 -> controller.todoCommandHandler(line);

                        // two arguments, record input as new t-odo object and add to the Item object list
                        default -> controller.createTodo(line);
                    }
                }
                default -> System.out.println("Can't understand command " + command);
            }
        } while (!command.equals("exit"));
    }  // end Main

}
