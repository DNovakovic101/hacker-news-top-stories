package pmf.novak101.hackernewstopstories.classes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface HackerNewsStoryDao {

    @Transaction
    @Query("SELECT * FROM HackerNewsStory")
    List<HackerNewsStory> getAll();

    @Insert
    void insertAll(HackerNewsStory... users);

    @Delete
    void delete(HackerNewsStory user);

    @Transaction
    @Query("SELECT * FROM HackerNewsStory WHERE storyID = :storyID")
    HackerNewsStory selectById(int storyID);

}
