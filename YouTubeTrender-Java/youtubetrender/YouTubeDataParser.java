package youtubetrender;

import javax.json.*;
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
            for (int i = 0; i < items.size(); i++) {
                /** parse to a single YouTubeVideo Object */
                // get JSON object at the index i
                JsonObject itemObj = items.getJsonObject(i);
                // create obj for data in 'snippet' (item -> snippet)
                JsonObject snippet = itemObj.getJsonObject("snippet");
                // parse to variables of a YouTubeVideo object
                String date = snippet.getString("publishedAt");
                String channelId = snippet.getString("channelId");
                String title = snippet.getString("title");
                String description = snippet.getString("description");
                String channelTitle = snippet.getString("channelTitle");

                // create obj for data in 'statistics' (item -> statistic)
                JsonObject stat = itemObj.getJsonObject("statistics");
                // parse to viewCount variable of YouTubeVideo object
                int viewCount = Integer.parseInt(stat.getString("viewCount"));

                // create and add the parsed YouTubeVideo object to list
                YouTubeVideo youtubeObject = new YouTubeVideo(channelTitle, date, title, description, channelId,viewCount);
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

    /**Method to clear data for parsing new JSON file*/
    public void emptyData(){ list.clear(); }

}// end YouTubeDataParser class
/*******************************************************************************************************************/
/**COMPARATOR CLASS sort videos based on PUBLISHED DATE
 * */
class YouTubeVideoDateComparator implements Comparator<YouTubeVideo> {
    @Override
    public int compare(YouTubeVideo obj1, YouTubeVideo obj2) {
        return obj2.getDate().compareTo(obj1.getDate());
    }

}

/**COMPARATOR CLASS sort videos based on CHANNEL TITLE
 * */
class YouTubeVideoChannelTitleComparator implements Comparator<YouTubeVideo>{
    @Override
    public int compare(YouTubeVideo obj1, YouTubeVideo obj2) {
        return obj2.getChannelTitle().toLowerCase().compareTo(obj1.getChannelTitle().toLowerCase());
    }
}
/**COMPARATOR CLASS sort videos based on NUMBER OF VIEWS (highest view number showed first)
 * */
class YouTubeVideoViewCountComparator implements Comparator<YouTubeVideo>{
    @Override
    public int compare(YouTubeVideo obj1, YouTubeVideo obj2) {
        return Integer.compare(obj2.getViewCount(), obj1.getViewCount());
    }
}

/**COMPARATOR CLASS sort videos based on DESCRIPTION LENGTH (WORD COUNT) - short description showed fist
 * */
class YouTubeVideoDescriptionLengthComparator implements Comparator<YouTubeVideo>{
    @Override
    public int compare(YouTubeVideo obj1, YouTubeVideo obj2) {
        return Integer.compare(obj2.getDescription().length(), obj1.getDescription().length());
    }
}