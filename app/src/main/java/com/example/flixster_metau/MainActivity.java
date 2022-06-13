package com.example.flixster_metau;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster_metau.adapters.MovieAdapter;
import com.example.flixster_metau.modules.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlin.text.UStringsKt;
import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    // review placeholder

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";
    List<Movie> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // review placeholder
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies); // Can we use view binding to avoid these tedious `findViewById`  
        movies = new ArrayList<>();
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient(); //Why are we initializing an `AsyncHttpClient` in this activity 
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            // review placeholder
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess"); //Let's strip these code when pushing code to production, aka. your repo in the future.
                //or you may us if(mDebug) {Log.d(...)} to only enable this in debugging. Excessive logging can be putting 
                //performance bottlenecks to apps
                JSONObject jsonObject = json.jsonObject; // we can directly json.jsonObject.getJSONArray("results");, in Line 54
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG,"Results : + " + results.toString()); //ditto
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG,"Movies: " + movies.size()); //ditto
                } catch (JSONException e) {
                    Log.e(TAG,"Hit json exception",e);
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG,"onFailure");

            }
        });


    }
}



