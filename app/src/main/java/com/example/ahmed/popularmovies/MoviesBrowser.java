package com.example.ahmed.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.ahmed.popularmovies.DataBase.FavoriteMovies;

import java.util.ArrayList;
import java.util.List;

public class MoviesBrowser extends AppCompatActivity {


    List<Movie> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_browser);

        getUrls geturls = new getUrls();

        geturls.execute("popularity.desc");


    }

    public class getUrls extends AsyncTask<String, Void, List<String>> {


        protected List<String> doInBackground(String... params) {
            List<String> urlslist = new ArrayList<String>();
            try {


                Data MoviesData = new Data(params[0]);
                movies = MoviesData.getMovies();

                urlslist = getUrls(movies);


            } catch (Exception e) {
                Log.v("log", "Built URI " + e);
            }
            return urlslist;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);

            UpdateView(strings);

        }

    }


    public void UpdateView(List<String> urls) {

        try {
            GridView gv = (GridView) findViewById(R.id.grid_view);
            gv.setAdapter(new SampleGridViewAdapter(this, urls));
            Log.v("log", "Built URI " + "1");

            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    if (findViewById(R.id.fragment_details) != null) {
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        Movie movie = movies.get(position);
                        Fragment fragment = MovieDetailsFragment.newInstance(movie);
                        fragmentManager.beginTransaction().add(R.id.fragment_details, fragment).commit();




                    } else {


                        Movie mov = movies.get(position);

                        Intent intent = new Intent(MoviesBrowser.this, MovieDetailsActivity.class);
                        intent.putExtra("movie", mov);
                        startActivity(intent);

//                    Intent detail = new Intent(MoviesBrowser.this, MovieDetailsActivity.class);
//                    detail.putExtra("title", mov.getOriginalTitle());
//                    detail.putExtra("PlotSynopsis", mov.getPlotSynopsis());
//                    detail.putExtra("rating", mov.getUserRating());
//                    detail.putExtra("release_date", mov.getReleaseDate());
//                    detail.putExtra("poster", mov.getPosterUrl());
//                    startActivity(detail);
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    public List<String> getUrls(List<Movie> movs) throws Exception {
        List<String> Urls = new ArrayList<String>();
        movs.toArray();
        for (int i = 0; i < 20; i++) {
            Urls.add(movs.get(i).getPosterUrl());
        }
        return Urls;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_user_rating) {
            getUrls geturls = new getUrls();

            geturls.execute("vote_average.desc");

            return true;
        } else if (id == R.id.action_sort_popularity) {
            getUrls geturls = new getUrls();

            geturls.execute("popularity.desc");

            return true;
        }
        else if (id == R.id.action_get_favorite) {
         movies = FavoriteMovies.create(this).getMovies();
            UpdateView(getsUrls(movies));

            return true;
        }
        return false;
    }


     List<String> getsUrls (List<Movie> movs){
        List<String> Urls = new ArrayList<String>();
        List<Movie> mov =movs;
        mov.toArray();
        for (int i=0;i<mov.size();i++){
            Urls.add(mov.get(i).getPosterUrl());
        }
        return Urls;
    }
}
