package com.example.ahmed.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.popularmovies.DataBase.FavoriteMovies;
import com.example.ahmed.popularmovies.reviews.ReviewsActivity;
import com.example.ahmed.popularmovies.reviews.ReviewsFragment;
import com.example.ahmed.popularmovies.trailers.TrailersActivity;
import com.example.ahmed.popularmovies.trailers.TrailersFragment;
import com.squareup.picasso.Picasso;


public class MovieDetailsFragment extends Fragment {

        public static final String ARG_MOVIE = "arg_movie";
        private FavoriteMovies mManager;


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        public MovieDetailsFragment() {
        }

        public static MovieDetailsFragment newInstance(Movie movie) {
            MovieDetailsFragment fragment = new MovieDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(ARG_MOVIE, movie);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
            mManager = FavoriteMovies.create(getActivity());
            final Movie movie = getArguments().getParcelable(ARG_MOVIE);

//            TextView moviePlot = (TextView) rootView.findViewById(R.id.movie_plot);
//            moviePlot.setText("null".equals(movie.getPlotSynopsis()) ? "" : movie.getPlotSynopsis());

            TextView movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
            movieTitle.setText(movie.getOriginalTitle());

            final Button moviefavorite = (Button)rootView.findViewById(R.id.movie_favorit);


            if (mManager.isFavorite(movie)) {
                moviefavorite.setTextColor(Color.BLACK);
            } else {
                moviefavorite.setTextColor(Color.LTGRAY);
            }
            moviefavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mManager.isFavorite(movie)) {
                        mManager.remove(movie);
                        moviefavorite.setTextColor(Color.LTGRAY);
                    } else {
                        mManager.add(movie);
                        moviefavorite.setTextColor(Color.BLACK);
                    }
                }
            });

            TextView rating = (TextView) rootView.findViewById(R.id.movie_rating);
            rating.setText(String.valueOf("User Rating [ " + movie.getUserRating() + "/10 ]"));

            TextView releaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
            releaseDate.setText("Release Date [ " + ("null".equals(movie.getReleaseDate()) ? "N/A" : movie.getReleaseDate()) + " ]");

            TextView plot = (TextView) rootView.findViewById(R.id.movie_plot);
            rating.setText(String.valueOf(movie.getPlotSynopsis()));


            ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_poster);
            Picasso.with(getActivity()).load(movie.getPosterUrl()).error(R.drawable.error).into(imageView);


            rootView.findViewById(R.id.movie_trailers).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity().findViewById(R.id.fragment_details) != null) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        Fragment fragment = TrailersFragment.newInstance(movie.getId());
                        fragmentManager.beginTransaction().add(R.id.fragment_details, fragment).commit();
                    }
                    else {
                        Intent intent = new Intent(getActivity(), TrailersActivity.class);
                        intent.putExtra("extra_movie_id", movie.getId());
                        startActivity(intent);
                    }
                }
            });


            rootView.findViewById(R.id.movie_reviews).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getActivity().findViewById(R.id.fragment_details) != null) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        Fragment fragment = ReviewsFragment.newInstance(movie.getId());
                        fragmentManager.beginTransaction().add(R.id.fragment_details, fragment).commit();
                    }
                    else {
                        Intent intent = new Intent(getActivity(), ReviewsActivity.class);
                        intent.putExtra("extra_movie_id", movie.getId());
                        startActivity(intent);
                    }
                }
            });



            return rootView;
        }

    }
