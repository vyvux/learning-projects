package youtubetrender;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.*;

public class YouTubeVideoIndexer {
    /**Map to store all YouTubeVideo objects<Set></> associated with each word<String></>*/
    Map<String, Set<YouTubeVideo>> masterList = new LinkedHashMap<>();
    List<Map.Entry<String, Integer>> sortedMasterList = new ArrayList<>();
    /**Map to store all words<String></> and their associated counting times<Long></>*/
    Map<String, Long> wordsOfAllVideos = new HashMap<>();
    List<Map.Entry<String, Long>> sortedList = new ArrayList<>();


   public void sortWordsByCount(){
       List<Map.Entry<String, Long>> listOfEntries = new ArrayList<Map.Entry<String, Long>>(wordsOfAllVideos.entrySet());
       Collections.sort(listOfEntries, Comparator.comparing(Map.Entry<String, Long>::getValue).reversed());
       for (Map.Entry<String, Long> word : listOfEntries){
           sortedList.add(word);
       }
   }

   public void sortMasterListByNumberOfVideos(){
       Map<String,Integer> map = new HashMap<>();
       for (Map.Entry<String, Set<YouTubeVideo>> each : masterList.entrySet()){
           map.put(each.getKey(), masterList.get(each.getKey()).size());
       }
       List<Map.Entry<String, Integer>> sortedListByNumberOfVideos = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
       Collections.sort(sortedListByNumberOfVideos, Comparator.comparing(Map.Entry<String, Integer>::getValue).reversed());
       for (Map.Entry<String, Integer> item : sortedListByNumberOfVideos){
           sortedMasterList.add(item);
       }
   }


    public void index(YouTubeDataParser parser){
        Long current, add;
        // loop running through all YouTubeVideo objects of the parser
        for (YouTubeVideo item :  parser.list) {
            item.countingWordMethod(item.getCombineTitleDescription());
            Iterator<Map.Entry<String, Long>> iterator = item.getAllWordMap().entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Long> each = iterator.next();
                add = each.getValue();//additional times of a word
                //if the word exists in the Map
                if (wordsOfAllVideos.containsKey(each.getKey())){
                    current = wordsOfAllVideos.get(each.getKey());// get the current times of word
                    wordsOfAllVideos.put(each.getKey(), current+add); //assign new value of times
                } else {
                    wordsOfAllVideos.put(each.getKey(), add);
                }
                //
                if (masterList.containsKey(each.getKey())) {
                    Set<YouTubeVideo> videos = masterList.get(each.getKey());
                    videos.add(item);
                    masterList.put(each.getKey(), videos);
                } else {
                    Set<YouTubeVideo> newVideos = new HashSet<>();
                    newVideos.add(item);
                    masterList.put(each.getKey(), newVideos);
                }
            }// end while
        }
    } // end index method

    public void findWord(String str){
        if (wordsOfAllVideos.containsKey(str)){
            System.out.println("The word \"" +str + "\" exists in the list of YouTube Videos");
        } else {
            System.err.println("The word \"" +str + "\"  does not exist in any YouTube Video");
        }
    }

    public void findCount(String str){
        if (wordsOfAllVideos.containsKey(str)){
            System.out.println("The word \""+str+"\" appears "+wordsOfAllVideos.get(str)+" times");
        } else {
            System.err.println("The word \"" +str + "\"  does not exist in any YouTube Video");
        }
    }

    public void findVideosAssociatedWithWord(String str){
        if (wordsOfAllVideos.containsKey(str)){
            System.out.println("The word \""+str+"\" appears in "+masterList.get(str).size()+" videos");
            System.out.println(masterList.get(str));
        } else {
            System.err.println("The word \"" +str + "\"  does not exist in any YouTube Video");
        }

    }

    public String findMostTrendingWordByCount(){
       return sortedList.get(0).getKey();
    }

    public void findMostTrendingWordByAssociatedVideos(){
        System.out.println("The word \""+sortedMasterList.get(0).getKey()+"\" has the highest video appearance with "+sortedMasterList.get(0).getValue()+" videos");
    }


    public void printSortedList(){
        for (Map.Entry<String, Long> word : sortedList){
            System.out.println(word);
        }
    }

}
