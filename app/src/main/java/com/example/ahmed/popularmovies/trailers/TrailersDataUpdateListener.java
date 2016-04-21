package com.example.ahmed.popularmovies.trailers;

import java.util.Map;

/**
 * Created by DiaaELsayed on 12/28/15.
 */
public interface TrailersDataUpdateListener {
    void onDataSetUpdated(Map<String, String> trailers);
}
