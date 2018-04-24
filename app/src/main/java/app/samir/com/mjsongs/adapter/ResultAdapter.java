package app.samir.com.mjsongs.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import app.samir.com.mjsongs.Constants;
import app.samir.com.mjsongs.R;
import app.samir.com.mjsongs.Utils;
import app.samir.com.mjsongs.model.Result;


public class ResultAdapter extends BaseAdapter {

    private Context mContext;

    private List<Result> resultList;
    private HashMap<Integer, ImageView> views;
    private HashMap<String, Bitmap> oldPhotos;
    private LayoutInflater mInflater;



    public ResultAdapter(Context context, List<Result> list) {
        mContext = context;
        resultList = list;
        views = new LinkedHashMap<>();
        oldPhotos = new LinkedHashMap<>();
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Result getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return resultList.get(position).getTrackNumber();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layoutItem;

        if (convertView == null) {
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.layout_single_item, parent, false);
        } else {
            layoutItem = (LinearLayout) convertView;
        }
        Result entry = resultList.get(position);

        TextView size = layoutItem.findViewById(R.id.name);
        size.setText(entry.getTrackName());

        ImageView v = layoutItem.findViewById(R.id.image);

        Bundle b = new Bundle();
        //url of the pict
        b.putString(Constants.BUNDLE_URL, entry.getArtworkUrl60());
        b.putInt(Constants.BUNDLE_POS, position);
        b.putInt(Constants.BUNDLE_ID, entry.getTrackNumber());
        views.put(position, v);

        new LoadImage().execute(b);

        return layoutItem;

    }



    private class LoadImage extends AsyncTask<Bundle, Void, Bundle> {

        @Override
        protected Bundle doInBackground(Bundle... b) {

            Bitmap bm;

            if(oldPhotos.get(b[0].getString(Constants.BUNDLE_URL))==null){
                bm = Utils.getBitMapFromUrl(b[0].getString(Constants.BUNDLE_URL));
                oldPhotos.put(b[0].getString(Constants.BUNDLE_URL),bm);
            }else{
                bm = oldPhotos.get(b[0].getString(Constants.BUNDLE_URL));
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.BUNDLE_BM, bm);
            bundle.putInt(Constants.BUNDLE_POS, b[0].getInt(Constants.BUNDLE_POS));

            return bundle;

        }

        @Override
        protected void onPostExecute(Bundle result) {
            super.onPostExecute(result);

            ImageView view = views.get(result.getInt(Constants.BUNDLE_POS));
            Bitmap bm = result.getParcelable(Constants.BUNDLE_BM);

            if (bm != null){
                view.setImageBitmap(bm);
            }else{
                Drawable placeholder = view.getContext().getResources().getDrawable(R.drawable.ic_launcher_background);
                view.setImageDrawable(placeholder);
            }

        }

    }
}