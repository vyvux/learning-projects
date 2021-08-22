package youtubetrender;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * The class to represent each distinctive word item, its count and associated videos
 */
public class YouTubeWordItem implements Comparable<YouTubeWordItem>{
    private final String word;
    private Set<YouTubeVideo> videos;
    private int count;

    public YouTubeWordItem(String word){
        this.word = word;
        videos = new HashSet<>();
        count = 1;
    }


    public Set<YouTubeVideo> getVideos() {
        return videos;
    }

    public void incrementCount(){
        count++;
    }

    /**
     * Method to add a video item to the list of associated videos
     * @param video YouTubeVideo object containing the word item
     */
    public void addVideo(YouTubeVideo video){
        videos.add(video);
    }

    /**
     * @return The Set of all associated video IDs and titles
     */
    public Set<String> getVideoIDs(){
        Set<String> videoList = new HashSet<>();
        for (YouTubeVideo item : videos){
            videoList.add(item.getTitle() + " - ID: "+ item.getId());
        }
        return videoList;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Word: \'" + word + "\'" +
                ", Number of occurrence: " + count +
                ", Number of videos: "+videos.size()+
                ", List: "+ getVideoIDs();
    }

    /**
     * Compare words based on occurrence
     */
    @Override
    public int compareTo(YouTubeWordItem o) {
        return Integer.compare(o.count, this.count);
    }
}

/**
 * The Comparator class to compare words based on their number of associated videos
 */
class WordItemMostPopularComparator implements Comparator<YouTubeWordItem>{

    @Override
    public int compare(YouTubeWordItem o1, YouTubeWordItem o2) {
//        return Integer.compare(o2.getVideos().size(), o1.getVideos().size());
        if (o2.getVideos().size() < o1.getVideos().size())
            return -1;
        else if (o2.getVideos().size() > o1.getVideos().size())
            return 1;
        else  return Integer.compare(o2.getCount(), o1.getCount());
    }
}
