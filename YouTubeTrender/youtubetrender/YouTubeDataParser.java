package youtubetrender;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class YouTubeDataParser {
    public List<YouTubeVideo> list = new ArrayList<YouTubeVideo>();
    public List<YouTubeVideo> parse(String filename) throws YouTubeDataParserException{
//        public YouTubeVideo parse(String filename) throws YouTubeDataParserException{ //-->test single YoutubeVideo Object

        try{
            // read data
            JsonReader jsonReader = Json.createReader(new FileInputStream(filename));
            JsonObject jobj = jsonReader.readObject();
            //read the values of the item field for multiple YouTubeVideo objects
            JsonArray items = jobj.getJsonArray("items");
            // loop for running all JSON objects in JSON array
            System.out.println("item size = " + items.size());
            for (int i = 0; i < items.size(); i++) {
                YouTubeVideo youtubeObject = new YouTubeVideo();
                /** parse to a single YouTubeVideo Object
                 */
                // get JSON object at the index i
                JsonObject itemObj = items.getJsonObject(i);
                // create obj for data in 'snippet' (item -> snippet)
                JsonObject snippet = itemObj.getJsonObject("snippet");
                // parse to variables of a YouTubeVideo object
                youtubeObject.setDate(snippet.getString("publishedAt"));
                youtubeObject.setChannelId(snippet.getString("channelId"));
                youtubeObject.setTitle(snippet.getString("title"));
                youtubeObject.setDescription(snippet.getString("description"));
                youtubeObject.setChannelTitle(snippet.getString("channelTitle"));
                youtubeObject.setCombineTitleDescription(); //join title and description for indexing performance

                // create obj for data in 'statistics' (item -> statistic)
                JsonObject stat = itemObj.getJsonObject("statistics");
                // parse to viewCount variable of YouTubeVideo object
                youtubeObject.setViewCount(Integer.parseInt(stat.getString("viewCount")));
                // add index of objects
                youtubeObject.setIndex(i+1);
                // count description length
                youtubeObject.setDescriptionWordCount();
                //concat title and description for later indexing
                youtubeObject.setCombineTitleDescription();
                // add the parsed YouTubeVideo object to list
                list.add(youtubeObject);
            } // end for loop
        } catch (FileNotFoundException e) {
            throw new YouTubeDataParserException("Parsing Error >>> File Not Found");
        } catch (JsonParsingException e){
            throw new YouTubeDataParserException("Parsing Error >>> Malformed Data at "+ e.getLocation());
        }
        return list;

    }

    public String toString(){ return list.toString();}

    public static class YouTubeDataParserException extends Exception{
        public YouTubeDataParserException (String message){
            super(message);
        }
    }

    /**Method to clear data for parsing new JSON file*/
    public void emptyData(){ list.clear(); }

    /**Method sort videos based on PUBLISHED DATE
     * */
    public List<YouTubeVideo> sortByDate() {
        Collections.sort(list, new YouTubeVideoDateComparator());
        return list;
    }

    /**Method sort videos based on CHANNEL TITLE
     * */
    public List<YouTubeVideo> sortByChannelTitle() {
        Collections.sort(list, new YouTubeVideoChannelTitleComparator());
        return list;
    }

    /**Method sort videos based on VIEW COUNTS
     * */
    public List<YouTubeVideo> sortByViewCount() {
        Collections.sort(list, new YouTubeVideoViewCountComparator());
        return list;
    }

    /**Method sort videos based on DESCRIPTION LENGTH
     * */
    public List<YouTubeVideo> sortByDescriptionLength() {
        Collections.sort(list, new YouTubeVideoDescriptionLengthComparator());
        return list;
    }
}// end YouTubeDataParser class
/*******************************************************************************************************************/
/**COMPARATOR CLASS sort videos based on PUBLISHED DATE
 * */
class YouTubeVideoDateComparator implements Comparator<YouTubeVideo> {
    @Override
    public int compare(YouTubeVideo obj1, YouTubeVideo obj2) {
        return obj1.getDate().compareTo(obj2.getDate());
    }

}

/**COMPARATOR CLASS sort videos based on CHANNEL TITLE
 * */
class YouTubeVideoChannelTitleComparator implements Comparator<YouTubeVideo>{
    @Override
    public int compare(YouTubeVideo obj1, YouTubeVideo obj2) {
        return obj1.getChannelTitle().toLowerCase().compareTo(obj2.getChannelTitle().toLowerCase());
    }
}
/**COMPARATOR CLASS sort videos based on NUMBER OF VIEWS (highest view number showed first)
 * */
class YouTubeVideoViewCountComparator implements Comparator<YouTubeVideo>{
    @Override
    public int compare(YouTubeVideo obj1, YouTubeVideo obj2) {
        return obj2.getViewCount()-(obj1.getViewCount());
    }
}

/**COMPARATOR CLASS sort videos based on DESCRIPTION LENGTH (WORD COUNT) - short description showed fist
 * */
class YouTubeVideoDescriptionLengthComparator implements Comparator<YouTubeVideo>{
    @Override
    public int compare(YouTubeVideo obj1, YouTubeVideo obj2) {
        return obj1.getDescriptionWordCount()-(obj2.getDescriptionWordCount());
    }
}