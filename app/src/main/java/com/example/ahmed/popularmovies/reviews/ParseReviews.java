package com.example.ahmed.popularmovies.reviews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParseReviews extends AsyncTask<Integer, Void, Map<String, String>> {

    private Activity mActivity;
    private ProgressDialog pd;
    private boolean ExceptionOccurred = false;
    private ReviewsUpdateListener listener;


    public ParseReviews(Activity activity, ReviewsUpdateListener listener) {
        mActivity = activity;
        this.listener = listener;
    }

    @Override
    protected Map<String, String> doInBackground(Integer... params) {
        ServerConnector connector = new ServerConnector(mActivity, params[0]);
        Map<String, String> trailers;
        try {
            trailers = connector.getReviews();
        } catch (IOException | JSONException e) {

            return new HashMap<>();
        } catch (Exception e) {
            ExceptionOccurred = true;
            return new HashMap<>();
        }

        return trailers;
    }

    @Override
    protected void onPostExecute(Map<String, String> trailers) {

        listener.onDataSetUpdated(trailers);
        if (pd != null) {
            pd.dismiss();
        }
    }
}
