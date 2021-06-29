package youtubetrender;

import java.util.Scanner;
import java.util.*;

public class YouTubeCLI {
    private Scanner input;
    private List<YouTubeVideo> list;
    private MenuPrinter printer;

    public YouTubeCLI(){
        input = new Scanner(System.in);
        list = new ArrayList<>();
        printer = new MenuPrinter();
        printer.welcome();
    }

    public void run(){
        parsingMode();
        mainMenuSelection();
    }


    public void parsingMode() {
        printer.parseMenu();
        // parse based on selection of files
        YouTubeDataParser parser = new YouTubeDataParser();
        boolean successfullyParse;
        do {
            successfullyParse = true;
            String filepath = input.next();
            switch (filepath) {
                case "1" -> filepath = "data/youtubedata.json";
                case "2" -> filepath = "data/youtubedata_15_50.json";
                case "3" -> filepath = "data/youtubedata_malformed.json"; // malformed file
                case "4" -> filepath = "data/youtubedata_indextest.json";
                case "5" -> filepath = "data/youtubedata_loremipsum.json";
                case "Q" -> quit();
            }
            try {
                list = parser.parse(filepath);
            } catch (YouTubeDataParserException e) {
                System.out.println("Parsing fail");
                System.out.println(e.getMessage());
//                System.out.println("Please select among provided options or enter valid file path");
                successfullyParse = false;
            }
        } while (!successfullyParse);
        System.out.println("Number of videos: " + list.size());

    }

    /**
     *
     */
    public void mainMenuSelection(){
        printer.mainMenu();
        // choose mode based on selection
        switch (input.next()){
            case "1" -> {
                printVideoList();
                mainMenuSelection();
            }
            case "2" -> sortingMode();
            case "3" -> analyseMode();
            case "4" -> run();
            case "5" -> quit();
            default -> {
                System.out.println("Invalid command. Please select among above options.");
                mainMenuSelection();
            }
        }
    }

    public void printVideoList(){
        for (YouTubeVideo item : list){
            System.out.println(item.toString());
        }
    }

    public void printVideoInfo(){
        for (YouTubeVideo item : list){
            System.out.println(item.getId()+" - "+item.getTitle());
        }
    }


    public void sortingMode(){
        printer.sortingMenu();
        // sorting based on features
        switch (input.next()){
            case "1" -> {
                list.sort(new YouTubeVideoDateComparator());
                System.out.println("---Sorting by Published Date---");
                printVideoInfo();
            }
            case "2" -> {
                list.sort(new YouTubeVideoChannelTitleComparator());
                System.out.println("---Sorting by Channel Title---");
                printVideoInfo();
            }
            case "3" -> {
                list.sort(new YouTubeVideoViewCountComparator());
                System.out.println("---Sorting by Number of Views---");
                printVideoInfo();
            }
            case "4" -> {
                list.sort(new YouTubeVideoDescriptionLengthComparator());
                System.out.println("---Sorting by Description Length---");
                printVideoInfo();
            }
            case "B" -> mainMenuSelection();
            case "6" -> quit();
            default -> System.out.println("Invalid Command. Please select among above options.");

        }
        sortingMode();
    }


    public void analyseMode(){
        printer.analysingMenu();
        YouTubeVideoIndexer indexer = new YouTubeVideoIndexer(list);
        // analyse based on features
        switch (input.next()){
            case "1" -> {
                System.out.print("Enter a word:");
                String word = input.next();
                indexer.findWord(word);
            }
            case "2" -> indexer.findMostUsedWord();
            case "3" -> indexer.findMostPopularWord();
            case "4" -> indexer.findWordListSortedByCount();
            case "5" -> indexer.findPopularWords();
            case "B" -> mainMenuSelection();
            case "Q" -> quit();
            default -> System.out.println("Invalid Command. Please select among above options.");
        }
        analyseMode();

    }

    public void quit(){
        System.out.println("\n-----EXIT APPLICATION-----");
        input.close();
        System.exit(0);
    }
}
