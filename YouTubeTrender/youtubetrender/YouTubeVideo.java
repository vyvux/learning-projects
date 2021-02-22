package youtubetrender;

import java.util.*;

public class YouTubeVideo {
    String channelTitle;
    String date;
    String title;
    String description;
    String channelId;
    String combineTitleDescription;
    int viewCount;
    int index;
    int descriptionWordCount = 0;
    // the collection to store all different words of a string
    Map<String, Long> allWordsOfaVideo = new HashMap<>();

    public Map<String, Long> getAllWordMap(){ return allWordsOfaVideo;}


    public void setDescriptionWordCount() {
        String[] words = description.trim().split("\\W+");
        descriptionWordCount = words.length;
    }

    public int getDescriptionWordCount(){ return descriptionWordCount;}

    public int getIndex() { return index; }

    public void setIndex(int index) { this.index = index; }



    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String id) {
        this.channelId = id;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channel) {
        this.channelTitle = channel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.toLowerCase();
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setCombineTitleDescription(){ combineTitleDescription = (title+" "+description).toLowerCase(); }

    public String getCombineTitleDescription(){
        return combineTitleDescription;
    }


    @Override
    public String toString() {
        return "----------YouTubeVideo #"+getIndex()+" {\n" +
                ">>>> channelId = '" + getChannelId() + "\'\n" +
                ">>>> channelTitle = '" + getChannelTitle() + "\'\n" +
                ">>>> publishedAt = '" + getDate() + "\'\n" +
                ">>>> title = '" + getTitle() + "\'\n" +
                ">>>> description = '" + getDescription() + "\'\n" +
                "   > description word count = "+ getDescriptionWordCount()+  "\n" +
                ">>>> viewCount = " + getViewCount()+
                '}'+"\n=======================================================================================================================\n";
    }

    /**Method for counting number of words of a string
     * AND store all different words in a HashSet
     * */
    public void countingWordMethod(String str){
        String lowerCaseStr = str.toLowerCase();
        Long count;
        //array to store all words of the string
        String[] words = lowerCaseStr.trim().split("\\W+");
        for (String item : words) {
            count = allWordsOfaVideo.get(item);
            if (allWordsOfaVideo.containsKey(item)){
                allWordsOfaVideo.put(item,count+ (long)1);
            } else {
                allWordsOfaVideo.put(item, (long)1);
            }
        }
    }


}
