package com.example.flixster_metau;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixster_metau.databinding.ActivityDetailBinding;
import com.example.flixster_metau.modules.Movie;
import org.parceler.Parcels;
public class MovieDetailActivity extends AppCompatActivity {

    private ActivityDetailBinding activityDetailBinding;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityDetailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = activityDetailBinding.getRoot();
        setContentView(view);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        activityDetailBinding.tvTitle.setText(movie.getTitle());
        activityDetailBinding.tvOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        activityDetailBinding.rbVoteAverage.setRating(voteAverage / 2.0f);
    }
    }
