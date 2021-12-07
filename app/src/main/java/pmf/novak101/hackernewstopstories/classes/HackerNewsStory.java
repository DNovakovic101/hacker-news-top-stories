package pmf.novak101.hackernewstopstories.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class HackerNewsStory {
    @PrimaryKey(autoGenerate = true)
    private int storyID;

    @ColumnInfo(name = "story_url")
    private String storyURL;

    @ColumnInfo(name = "story_title")
    private String storyTitle;

    @ColumnInfo(name = "story_score")
    private int storyScore;

    public HackerNewsStory( String storyURL, String storyTitle, int storyScore) {
        this.storyURL = storyURL;
        this.storyTitle = storyTitle;
        this.storyScore = storyScore;
    }


    public int getStoryID() {
        return storyID;
    }

    public String getStoryURL() {
        return storyURL;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public int getStoryScore() {
        return storyScore;
    }

    public void setStoryID(int storyID) {
        this.storyID = storyID;
    }

    public void setStoryURL(String storyURL) {
        this.storyURL = storyURL;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public void setStoryScore(int storyScore) {
        this.storyScore = storyScore;
    }

    @Override
    public String toString() {
        return "HackerNewsStory{" +
                "storyID=" + storyID +
                ", storyURL='" + storyURL + '\'' +
                ", storyTitle='" + storyTitle + '\'' +
                ", storyScore=" + storyScore +
                '}';
    }
}
