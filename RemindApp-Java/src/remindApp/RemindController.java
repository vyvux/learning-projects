package remindApp;

import java.util.*;

public class RemindController {
    ArrayList<Item> list = new ArrayList<>();

    public void printAll(){
        if (list.isEmpty()) System.out.println("No item found");
        else{
            for (Item item: list){
                item.print();
            }
        }
    }

    public void processMemo(String content, int count){
        if (count == 1) {
            for (Item item : list) {
                if (item instanceof Memo) item.print();
            }
        } else {
            Memo memo = new Memo(content);
            list.add(memo);
        }
    }

    public void processPhone(String content, int count){
        switch (count){
            case 1 -> {
                ArrayList<Phone> phones = phoneList();
                for (Phone phone: phones) phone.print();
            }

            case 2 -> {
                if (content.equals("-a")){
                    ArrayList<Phone> phones = phoneList();
                    Collections.sort(phones);
                    for (Phone phone: phones) phone.print();
                } else {
                    for (Phone contact: phoneList()){
                        if (contact.name.startsWith(content)) contact.print();
                    }
                }
            }

            case 3 -> {
                Phone phone = new Phone(content);
                list.add(phone);
            }
            default -> System.out.println("incorrect command");
        }
    }

    public void processTodo(String content, int count){
        switch (count){
            case 1 -> {
                ArrayList<Todo> todos = todoList();
                for (Todo todo: todos) todo.print();
            }
            case 2 ->{
                if (content.equals("-d")){
                    ArrayList<Todo> todos = todoList();
                    Collections.sort(todos);
                    for (Todo todo: todos) todo.print();
                } else{
                    for (Todo todo: todoList()){
                        if (todo.hasPreviousCurrentFollowingDate(content)) todo.print();
                    }
                }
            }
            default -> {
                Todo todo = new Todo(content);
                list.add(todo);
            }
        }

    }

    public ArrayList<Phone> phoneList(){
        ArrayList<Phone> phones = new ArrayList<>();
        for (Item item: list) {
            if (item instanceof Phone) phones.add((Phone) item);
        }
        return phones;
    }


    public ArrayList<Todo> todoList(){
        ArrayList<Todo> todos = new ArrayList<>();
        for (Item item: list) {
            if (item instanceof Todo) todos.add((Todo) item);
        }
        return todos;
    }

}
