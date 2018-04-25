package app.samir.com.mjsongs;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import app.samir.com.mjsongs.model.Result;

public class ResultDetailActivity extends AppCompatActivity {

    private static final String TAG = "ResultDetailActivity";

    private ImageView imageView_artworkUrl100;
    private TextView textView_trackName;
    private TextView textView_trackCensoredName;
    private TextView textView_artistName;
    private TextView textView_ccollectionPrice;
    private TextView textView_collectionName;
    private TextView textView_releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Song Details");
        }
        initViews();

        if(getIntent() != null){

            Result result = getIntent().getParcelableExtra("result");



           // imageView_artworkUrl100.setImageResource(0);
            new LoadImage().execute(result.getArtworkUrl100());

            textView_trackName.setText(result.getTrackName());
            textView_trackCensoredName.setText(result.getTrackCensoredName());
            textView_artistName.setText(result.getArtistName());
            textView_ccollectionPrice.setText(String.format("$ %s", String.valueOf(result.getCollectionPrice())));
            textView_collectionName.setText(result.getCollectionName());
            textView_releaseDate.setText(result.getReleaseDate());
        }

    }

    private void initViews() {
        imageView_artworkUrl100 = findViewById(R.id.image);

        textView_trackName  = findViewById(R.id.track_name);
        textView_trackCensoredName  = findViewById(R.id.track_censor_name);
        textView_artistName  = findViewById(R.id.artist_name);
        textView_ccollectionPrice  = findViewById(R.id.collection_price);
        textView_collectionName  = findViewById(R.id.collection_name);
        textView_releaseDate  = findViewById(R.id.release_date);
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap>{


        @Override
        protected Bitmap doInBackground(String... strings) {
            return  Utils.getBitMapFromUrl(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap != null){
                imageView_artworkUrl100.setImageBitmap(bitmap);
            }

        }
    }
}
