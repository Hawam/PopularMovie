package com.example.ahmed.popularmovies;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Data  {
    static final String baseUrl = "http://api.themoviedb.org/3/discover/movie";

    private final String apiKay = "e7c2f41f4e030767ca9a04daa7f59870";
    private String sortby="popularity.desc";

    public Data(String s) {
        // No instances.
        sortby = s;
    }

    String getData() throws  Exception {
        //String baseUrl = "http://api.themoviedb.org/3/discover/movie";
        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("sort_by", sortby)
                .appendQueryParameter("api_key",apiKay).build();

        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();
        httpURLConnection.connect();

        int responseCode;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            responseCode = httpURLConnection.getResponseCode();
        }

        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
                return stringBuilder.toString();
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                throw new Exception();
            default:
                throw new IllegalStateException("Connection method is not equipped to handle this case");
        }
    }

    public List<Movie> getMovies() throws  Exception {


        List<Movie> movies = new ArrayList<>();
        movies.addAll(new JsonParser(getData()).getMovies());

        return movies;
    }

    public  List<String> getUrls ()throws Exception{
        List<String> Urls = new ArrayList<String>();
        List<Movie> mov =getMovies();
        mov.toArray();
        for (int i=0;i<20;i++){
            Urls.add(mov.get(i).getPosterUrl());
        }
        return Urls;
    }
}
