package com.example.ahmed.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

final class SampleGridViewAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> urls = new ArrayList<String>();



    public SampleGridViewAdapter(Context context , List<String> Data) {
        this.context = context;
        urls.addAll(Data);
        Log.v("log", "URIs " + urls);
    }



    @Override
     public View getView(int position, View convertView, ViewGroup parent) {
      SquaredImageView view = (SquaredImageView) convertView;
      if (view == null) {
          view = new SquaredImageView(context);
          view.setScaleType(CENTER_CROP);
      }

      // Get the image URL for the current position.
      String url = getItem(position);

      // Trigger the download of the URL asynchronously into the image view.
      Picasso.with(context) //
          .load(url) //
          .placeholder(R.drawable.placeholder) //
          .error(R.drawable.error) //
          .fit() //
          .tag(context) //
          .into(view);

    return view;
    }

  @Override
  public int getCount() {
    return urls.size();
  }

  @Override
  public String getItem(int position) {
    return urls.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

}
