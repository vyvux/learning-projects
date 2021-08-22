package youtubetrender;

public class YouTubeVideo {
    String channelTitle;
    String date;
    String title;
    String description;
    String id;
    int viewCount;
    int descriptionWordCount = 0;

    public YouTubeVideo(String channelTitle, String date, String title, String description, String id, int viewCount) {
        this.channelTitle = channelTitle;
        this.date = date;
        this.title = title;
        this.description = description;
        this.id = id;
        this.viewCount = viewCount;
    }


    public String getId() {
        return id;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getViewCount() {
        return viewCount;
    }


    @Override
    public String toString() {
        return "----------YouTubeVideo" + " {\n" +
                ">>>> channelId = '" + id + "\'\n" +
                ">>>> channelTitle = '" + channelTitle + "\'\n" +
                ">>>> publishedAt = '" + date + "\'\n" +
                ">>>> title = '" + title + "\'\n" +
                ">>>> description = '" + description + "\'\n" +
                ">>>> viewCount = " + viewCount +
                '}' + "\n=======================================================================================================================\n";
    }

}
