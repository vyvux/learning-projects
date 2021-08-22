package youtubetrender;

public class MenuPrinter {
    public void welcome(){
        System.out.println("----------------------------------------------");
        System.out.println("|   STARTING YOUTUBE TRENDER APPLICATION     |");
        System.out.println("|         -Enter Q at anytime to Quit-       |");
        System.out.println("----------------------------------------------");
    }
    public void parseMenu(){
        System.out.println("---CHOOSE A FILE FOR PARSING---\n"+
                "1. youtubedata.json\n"+
                "2. youtubedata_15_50.json\n"+
                "3. youtubedata_malformed.json\n"+
                "4. youtudedata_indextest.json\n"+
                "5. youtubedata_loremipsum.json\n"+
                "Q. Quit\n"+
                "Or enter file path:\n");
    }

    public void mainMenu(){
        System.out.println( "---MAIN MENU---\n"+
                "1. View current list of videos\n"+
                "2. Sort videos by different features\n"+
                "3. Analyse trending topics\n"+
                "4. Change new JSON file\n" +
                "Q. Quit");
    }

    public void sortingMenu(){
        System.out.println( "---SORT BY MENU---\n" +
                "1. Sort by Published Date\n" +
                "2. Sort by Channel Title\n" +
                "3. Sort by Number of Views\n" +
                "4. Sort by Description Length\n"+
                "B. Back to main menu\n"+
                "Q. Quit");
    }

    public void analysingMenu(){
        System.out.println("---ANALYSE MENU---\n"+
                "1. Find occurrence and associated videos of a word\n"+
                "2. Find the most used word (highest occurrence)\n"+
                "3. Find the most used word (highest number of associated videos)\n"+
                "4. List of words sorted by occurrence\n"+
                "5. List of words sorted by number of associated videos\n"+
                "B. Back to main menu\n"+
                "Q. Quit");
    }


}
