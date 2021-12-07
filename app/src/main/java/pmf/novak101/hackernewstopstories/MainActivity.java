package pmf.novak101.hackernewstopstories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pmf.novak101.hackernewstopstories.classes.HackerNewsStory;
import pmf.novak101.hackernewstopstories.classes.ItemClickListener;
import pmf.novak101.hackernewstopstories.classes.SingletonDB;
import pmf.novak101.hackernewstopstories.classes.StoryAdapter;
import pmf.novak101.hackernewstopstories.classes.StoryDatabase;



public class MainActivity extends AppCompatActivity implements ItemClickListener {
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    RecyclerView recyclerViewMain;

    List<HackerNewsStory> storyList = new ArrayList<HackerNewsStory>();

    // No outside use but for prepping the 'real' storyList but needed as global vars
    List<Integer> storyIds = new ArrayList<Integer>();
    JSONArray storyIdsJSONARray;

    StoryDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerViewMain = findViewById(R.id.recyclerViewMain);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(llm);

        executorService.execute(() -> {
            db = SingletonDB.getStoryDatabase(getApplicationContext());

            // Run only once to get the stories written to db
            writeStoriesToDatabase();
        });





    }

    public void writeStoriesToDatabase() {
        executorService.execute(() -> {
            // Calls api to get top 500 story IDs
            populateStoryIDs();
            //Calls API to get stories for number of IDs
            populateHackerNewsStoryArray();

            for(HackerNewsStory story : storyList) {
                db.storyDao().insertAll(story);
            }

            mainThreadHandler.post(() -> {
                recyclerViewMain.setAdapter(new StoryAdapter( (ArrayList<HackerNewsStory>) storyList, this));
                recyclerViewMain.setOnClickListener(v -> {

                });
            });

        });

    }

    // Must Be called outside of the main thread exec
    public void populateStoryIDs() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("https://hacker-news.firebaseio.com/v0/topstories.json");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            storyIdsJSONARray = new JSONArray(reader.readLine());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            try {
                if(reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    // Must Be called outside of the main thread exec
    public void populateHackerNewsStoryArray() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        int numOfStories = 20;
        for (int i = 0; i < numOfStories; i++) {

            try {
                URL url = new URL("https://hacker-news.firebaseio.com/v0/item/"+ storyIdsJSONARray.get(i).toString() +".json");
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                JSONObject storyJSON = new JSONObject(reader.readLine());
                HackerNewsStory storyTemp = new HackerNewsStory(storyJSON.get("url").toString(),
                                                                storyJSON.get("title").toString(),
                                                                Integer.parseInt(storyJSON.get("score").toString()) );
                storyList.add(storyTemp);
            } catch (MalformedURLException e) {
                numOfStories++;
                e.printStackTrace();
            } catch (IOException e) {
                numOfStories++;
                e.printStackTrace();
            } catch (JSONException e) {
                numOfStories++;
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if(reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }


    @Override
    public View.OnClickListener onItemClicked(HackerNewsStory h) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", h.getStoryURL());
        startActivity(intent);
        return null;
    }
}