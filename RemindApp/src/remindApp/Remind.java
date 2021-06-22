package remindApp;

import java.util.*;

public class Remind {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String command;

        RemindController controller = new RemindController();

        do {
            System.out.print("\nCommand? ");
            StringTokenizer line = new StringTokenizer(input.nextLine());
            int count = line.countTokens();
            command = line.nextToken().toLowerCase();
            String content = null;
            if (count > 1) content = line.nextToken("").trim();
            switch (command) {
                case "exit" -> {
                }
                case "list" ->  controller.printAll();
                case "memo" ->  controller.processMemo(content, count);
                case "phone" -> controller.processPhone(content, count);
                case "todo" -> controller.processTodo(content, count);
                default -> System.out.println("Can't understand command " + command);
            }
        } while (!command.equals("exit"));
    }  // end Main

}
