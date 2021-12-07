package pmf.novak101.hackernewstopstories.classes;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {HackerNewsStory.class}, version = 1)
public abstract class StoryDatabase extends RoomDatabase {
    public abstract HackerNewsStoryDao storyDao();
}
