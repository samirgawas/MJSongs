package app.samir.com.mjsongs;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import app.samir.com.mjsongs.adapter.ResultAdapter;
import app.samir.com.mjsongs.model.Result;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setTitle("MJ Songs");
        }
            final ListView listView = findViewById(R.id.listview);
            String jsonString = Utils.loadJSONFromAsset(MainActivity.this);

            Gson gson = new Gson();
            JsonElement element = gson.fromJson (jsonString, JsonElement.class);
            JsonObject object = element.getAsJsonObject();
            JsonArray resultArray = object.get("results").getAsJsonArray();
            Type type = new TypeToken<ArrayList<Result>>() {
            }.getType();
            final ArrayList<Result> results = new Gson().fromJson(resultArray.toString(), type);
            Log.e(TAG, "onCreate: " + results);

            ResultAdapter resultAdapter = new ResultAdapter(MainActivity.this, results);
            listView.setAdapter(resultAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the selected item text from ListView

                    Result result = (Result) parent.getAdapter().getItem(position);
                    Intent intent = new Intent(MainActivity.this, ResultDetailActivity.class);
                    intent.putExtra("artworkUrl100", result.getArtworkUrl100());
                    intent.putExtra("trackName",result.getTrackName());
                    intent.putExtra("trackCensoredName",result.getTrackCensoredName());
                    intent.putExtra("artistName",result.getArtistName());
                    intent.putExtra("collectionName",result.getCollectionName());
                    intent.putExtra("collectionPrice",result.getCollectionPrice());
                    intent.putExtra("releaseDate",result.getReleaseDate());
                    intent.putExtra("country",result.getCountry());
                    intent.putExtra("currency",result.getCurrency());
                    startActivity(intent);
                }
            });
        }
}
