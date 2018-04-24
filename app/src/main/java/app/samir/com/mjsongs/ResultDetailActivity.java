package app.samir.com.mjsongs;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import app.samir.com.mjsongs.adapter.ResultAdapter;

public class ResultDetailActivity extends AppCompatActivity {

    private ImageView imageView_artworkUrl100;
    private TextView textView_trackName;
    private TextView textView_trackCensoredName;
    private TextView textView_artistName;
    private TextView textView_ccollectionPrice;
    private TextView textView_collectionName;
    private TextView textView_releaseDate;
    private TextView textView_currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);
        initViews();

        if(getIntent() != null){

            String artworkUrl100 = getIntent().getStringExtra("artworkUrl100");
            String trackName = getIntent().getStringExtra("trackName");
            String trackCensoredName = getIntent().getStringExtra("trackCensoredName");
            String artistName = getIntent().getStringExtra("artistName");
            String collectionName = getIntent().getStringExtra("collectionName");
            double collectionPrice = getIntent().getDoubleExtra("collectionPrice", 0);
            String releaseDate = getIntent().getStringExtra("releaseDate");
            String country = getIntent().getStringExtra("country");
            String currency = getIntent().getStringExtra("currency");

           // imageView_artworkUrl100.setImageResource(0);
            new LoadImage().execute(artworkUrl100);

            textView_trackName.setText(trackName);
            textView_trackCensoredName.setText(trackCensoredName);
            textView_artistName.setText(artistName);
            textView_ccollectionPrice.setText(String.format("$ %s", String.valueOf(collectionPrice)));
            textView_collectionName.setText(collectionName);
            textView_releaseDate.setText(releaseDate);
            textView_currency.setText(currency);
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
        textView_currency  = findViewById(R.id.currency);
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
