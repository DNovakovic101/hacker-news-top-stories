package pmf.novak101.hackernewstopstories.classes;

import android.content.Context;

import androidx.room.Room;

public  class SingletonDB {

    private static StoryDatabase db;

    public static StoryDatabase getStoryDatabase(Context ctx) {
        if(db == null) {
            db = Room.databaseBuilder(ctx,
                    StoryDatabase.class, "HackerNewsStory").build();
        }

            return db;
    }
}
