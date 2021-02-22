package youtubetrender;

import java.util.*;

public class YouTubeTrender {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws YouTubeDataParser.YouTubeDataParserException {
        Scanner input = new Scanner(System.in);
        System.out.println("YouTube Trender Application");
        YouTubeDataParser dataParser = new YouTubeDataParser();
        boolean loopRun, mainMenuLoopRun;
        do{
            dataParser.emptyData(); //clear data for reading in new JSON file
            loopRun = false;
            /**Choose a file to parse data in */
            parsingMode(dataParser);
            /**Main Function Menu*/
            do {
                mainMenuLoopRun = true;
                printFeatureMenu();
                switch (input.nextInt()){
                    case 1:/**print the current list of YouTube videos*/
                        System.out.println(dataParser.toString());
                        break;
                    case 2:/**Sorting feature*/
                        printSortMenu();
                        sortingMode(dataParser);
                        System.out.println(dataParser.toString());
                        break;
                    case 3:/**Analyse trending topics*/
                        printAnalyseMenu();
                        analyseMode(dataParser);
                        break;
                    case 4: /**change new file*/
                        mainMenuLoopRun = false; //stop feature menu loop
                        loopRun = true; // enter large loop
                        break;
                    case 5: /**QUIT*/
                        mainMenuLoopRun = false; //stop feature menu loop
                        loopRun = false; //stop large loop
                        break;
                    default:
                        System.err.println("ERROR: invalid choice of number. PLEASE CHOOSE AGAIN");
                        break;
                } // end switch
            } while (mainMenuLoopRun);
        } while (loopRun);

        System.out.println("---EXIT APPLICATION---");


    }
    /**---------------------END MAIN METHOD------------------------------------*/

    static void parsingMode(YouTubeDataParser parseObj) throws YouTubeDataParser.YouTubeDataParserException {
        Scanner in = new Scanner(System.in);
        boolean loopRun;
        System.out.println("---CHOOSE A FILE FOR PARSING---\n"+
                "1. youtubedata.json\n"+
                "2. youtubedata_15_50.json\n"+
                "3. youtubedata_singleitem.json\n"+
                "4. youtubedata_malformed.json\n"+
                "5. youtudedata_indextest.json\n"+
                "6. youtubedata_loremipsum.json\n");
        do {
            switch (in.nextInt()) {
                case 1:
                    parseObj.parse("data/youtubedata.json");
                    loopRun = false;
                    break;
                case 2:
                    parseObj.parse("data/youtubedata_15_50.json");
                    loopRun = false;
                    break;
                case 3:
                    parseObj.parse("data/youtubedata_singleitem.json");
                    loopRun = false;
                    break;
                case 4:
                    parseObj.parse("data/youtubedata_malformed.json");
                    loopRun = false;
                    break;
                case 5:
                    parseObj.parse("data/youtubedata_indextest.json");
                    loopRun = false;
                    break;
                case 6:
                    parseObj.parse("data/youtubedata_loremipsum.json");
                    loopRun = false;
                    break;
                default:
                    parseObj.parse("null");//file not found error
                    loopRun = true;
                    break;
            }
        } while (loopRun);
    }

    static void printFeatureMenu(){
        System.out.println( "---FEATURE MENU---\n"+
                "1. View current list of videos\n"+
                "2. Sort videos by different features\n"+
                "3. Analyse trending topics\n"+
                "4. Change new JSON file\n" +
                "5. Terminate the program");
    }

    static void printSortMenu(){
        System.out.println( "---SORT BY MENU---\n" +
                "1. Sort by Published Date\n" +
                "2. Sort by Channel Title\n" +
                "3. Sort by Number of Views\n" +
                "4. Sort by Description Word Count");
    }

    static void sortingMode(YouTubeDataParser parserObj){
        Scanner in = new Scanner(System.in);
        boolean loopRun;
        do {
            loopRun = false;
            switch (in.nextInt()) {
                case 1:
                    System.out.println("---Start sorting by Published Date---");
                    parserObj.sortByDate();
                    break;
                case 2:
                    System.out.println("---Start sorting by Channel Title (alphabetically)---");
                    parserObj.sortByChannelTitle();
                    break;
                case 3:
                    System.out.println("---Start sorting by Number of Views (highest to lowest)---");
                    parserObj.sortByViewCount();
                    break;
                case 4:
                    System.out.println("---Start sorting by Description Word Count---");
                    parserObj.sortByDescriptionLength();
                    break;
                default:
                    System.err.println("ERROR: invalid choice of number. PLEASE CHOOSE AGAIN");
                    loopRun = true;
                    break;
            } // end switch inside sorting selection
        } while (loopRun);

    }

    static void printAnalyseMenu(){
        System.out.println("---ANALYSE MENU---\n"+
                "1. Find if a word included in the list of videos\n"+
                "2. Find the count associated with a word\n"+
                "3. Find all the videos having a specific word\n"+
                "4. Mostly used word (word that has the highest count)\n"+
                "5. Mostly used word (word that has the highest number of associated videos)\n"+
                "6. Print list of words sorted by count");
    }

    static void analyseMode(YouTubeDataParser parserObj){
        Scanner in = new Scanner(System.in);
        YouTubeVideoIndexer indexer = new YouTubeVideoIndexer();
        indexer.index(parserObj);
        indexer.sortWordsByCount();
        boolean loopRun;
        do {
            switch (in.nextInt()){
                case 1://quickly find a word if it is trending or not
                    System.out.println("Enter a word for searching:");
                    indexer.findWord(in.next());
                    loopRun = false;
                    break;
                case 2://find count of a word
                    System.out.println("Enter a word for searching its count:");
                    indexer.findCount(in.next());
                    loopRun = false;
                    break;
                case 3://find videos having a word
                    System.out.println("Enter a word for searching its associated videos:");
                    indexer.findVideosAssociatedWithWord(in.next());
                    loopRun = false;
                    break;
                case 4://mostly used word based on count
                    System.out.println("The most trending word is \""+indexer.findMostTrendingWordByCount()+"\"");
                    loopRun = false;
                    break;
                case 5://mostly used word based on highest number of associated videos
                    indexer.sortMasterListByNumberOfVideos();
                    indexer.findMostTrendingWordByAssociatedVideos();
                    loopRun = false;
                    break;
                case 6://top trending words
                    System.out.println("Word list sorted by counts:");
                    indexer.printSortedList();
                    loopRun = false;
                    break;
                default:
                    System.err.println("ERROR: invalid choice of number. PLEASE CHOOSE AGAIN");
                    loopRun = true;
                    break;
            }// end switch
        } while (loopRun);
    }
}
