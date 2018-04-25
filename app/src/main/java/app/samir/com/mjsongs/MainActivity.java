package app.samir.com.mjsongs;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("MJ Songs list");
        }
        final ListView listView = findViewById(R.id.listview);
        String jsonString = Utils.loadJSONFromAsset(MainActivity.this);

        if (isNetworkAvailable()) {
            Gson gson = new Gson();
            JsonElement element = gson.fromJson(jsonString, JsonElement.class);
            JsonObject object = element.getAsJsonObject();
            JsonArray resultArray = object.get("results").getAsJsonArray();
            Type type = new TypeToken<ArrayList<Result>>() {
            }.getType();
            final ArrayList<Result> results = new Gson().fromJson(resultArray.toString(), type);

            ResultAdapter resultAdapter = new ResultAdapter(MainActivity.this, results);
            listView.setAdapter(resultAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Result result = (Result) parent.getAdapter().getItem(position);
                    Intent intent = new Intent(MainActivity.this, ResultDetailActivity.class);
                    intent.putExtra("result", result);
                    startActivity(intent);
                }
            });
        }else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
