package com.example.ahmed.popularmovies.trailers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.ahmed.popularmovies.R;


/**
 * Created by DiaaELsayed on 12/28/15.
 */
public class TrailersActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }


    protected Fragment createFragment() {
        return  TrailersFragment.newInstance(getIntent().getIntExtra("extra_movie_id",0));
    }
}
