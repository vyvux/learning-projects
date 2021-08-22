package youtubetrender;

import java.util.*;

/**
 * The class to perform analysing function for a list of video items
 */
public class YouTubeVideoIndexer {
    // The map to store each distinctive word and its YouTubeWordItem object
    private Map<String, YouTubeWordItem> map = new HashMap<>();
    // The list of videos to be indexed
    private List<YouTubeVideo> list;

    public YouTubeVideoIndexer(List<YouTubeVideo> list) {
        this.list = list;
        index();
    }

    /**
     * Method to index all words of the video titles and descriptions,
     * create new YouTubeWordItem object whenever a new word is reached,
     * increment the word count,
     * and add associate the current YouTubeVideo object with the word
     */
    public void index(){
        for (YouTubeVideo item : list) {
            String[] strings = (item.getTitle() + " " + item.getDescription()).trim().split("\\s+");
            for (String word : strings) {
                if (map.containsKey(word)) {
                    map.get(word).incrementCount();
                } else {
                    map.put(word, new YouTubeWordItem(word));
                }
                map.get(word).addVideo(item);
            }
        }
    }

    /**
     * @return List of words sorted by occurrence
     */
    public List<YouTubeWordItem> getSortedWordList(){
        List<YouTubeWordItem> sortedList = new ArrayList<>(map.values());
        Collections.sort(sortedList);
        return sortedList;
    }

    /**
     * Checks the existence of a word And print out the representation of the word item if found
     * @param word The search term
     */
    public void findWord(String word){
        if (map.containsKey(word)){
            System.out.println(map.get(word));
        } else {
            System.out.println("The word \'"+word+"\' doesn't exist");
        }
    }

    public void findMostUsedWord(){
        System.out.println("The most used word is:");
        System.out.println(getSortedWordList().get(0));
        // check if following items also has the same number of occurrence
        boolean continueChecking = true;
        for (int i = 1; i < getSortedWordList().size() && continueChecking; i++){
            if (getSortedWordList().get(i).getCount() == getSortedWordList().get(0).getCount()){
                System.out.println(getSortedWordList().get(i));
            } else continueChecking = false;
        }
    }


    public void findWordListSortedByCount(){
        System.out.println("List of words sorted by occurrence:");
        for (YouTubeWordItem item : getSortedWordList()){
            System.out.println(item);
        }
    }

    public void findMostPopularWord(){
        List<YouTubeWordItem> sortedList = new ArrayList<>(map.values());
        sortedList.sort(new WordItemMostPopularComparator());
        System.out.println("The word that appears in most of videos:");
        System.out.println(sortedList.get(0));
        // check if following items also has the same number of videos
        boolean continueChecking = true;
        for (int i = 1; i < sortedList.size() && continueChecking; i++){
            if (sortedList.get(i).getVideos().size() == sortedList.get(0).getVideos().size()){
                System.out.println(sortedList.get(i));
            } else continueChecking = false;
        }
    }

    public void findPopularWords(){
        System.out.println("List of popular words:");
        List<YouTubeWordItem> sortedList = new ArrayList<>(map.values());
        Collections.sort(sortedList, new WordItemMostPopularComparator());
        for (YouTubeWordItem item : sortedList){
            System.out.println(item);
        }

    }
}
