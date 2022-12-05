package com.example.cinemacachefinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReviewListAdapter reviewListAdapter;
    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREF_NAME = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        sharedPreferences = getSharedPreferences(
                SHARED_PREF_NAME,
                MODE_PRIVATE);
        recyclerView = findViewById(R.id.recycler_watchlist);
        reviewListAdapter = new ReviewListAdapter(this, getReviewList());
        recyclerView.setAdapter(reviewListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.navigation_top_movies) {
            Intent movieListIntent = new Intent(this, TopMovieListActivity.class);
            startActivity(movieListIntent);
            finish();
            return true;
        }
        else if (item.getItemId() == R.id.navigation_watchlist) {
            Intent movieListIntent = new Intent(this, WatchListActivity.class);
            startActivity(movieListIntent);
            finish();
            return true;
        }
        else if(item.getItemId() == R.id.navigation_reviews){
            Intent reviewIntent = new Intent(this, ReviewListActivity.class);
            startActivity(reviewIntent);
            finish();
            return true;
        }
        else if(item.getItemId() == R.id.navigation_find_movies){
            Intent findMoviesIntent = new Intent(this, MainActivity.class);
            startActivity(findMoviesIntent);
            finish();
            return true;
        }
        else if(item.getItemId() == R.id.navigation_logout){
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
            return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    public ArrayList<Review> getReviewList() {
        ArrayList<Review> reviewList = new ArrayList<>();
        String reviewListData = sharedPreferences.getString("REVIEWS", null);
        if(!reviewListData.isEmpty()){
            String[] reviewsDataList = reviewListData.split("-done-");
            for (String review : reviewsDataList) {
                String[] reviewData = review.split("-break-");
                String reviewText = reviewData[0];
                Movie reviewMovie = getSelectedMovie(reviewData[1]);
                String reviewRating = reviewData[2];
                Review r = new Review(reviewText, reviewMovie, reviewRating);
                reviewList.add(r);
            }
        }
        System.out.println(sharedPreferences.getString("REVIEWS", null));
        return reviewList;
    }



    public Movie getSelectedMovie(String movieToMatch) {
        try {
            JSONObject jsonObject = new JSONObject(loadMoviesFromJSON());
            JSONArray jsonArray = jsonObject.getJSONArray("movies");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieData = jsonArray.getJSONObject(i);
                String movieTitle = movieData.getString("movieTitle");
                if(movieTitle.equals(movieToMatch)){
                    String releaseDate = movieData.getString("releaseDate");
                    String director = movieData.getString("director");
                    String description = movieData.getString("description");
                    String rating = movieData.getString("rating");
                    String genre = movieData.getString("genre");
                    String posterFileName = movieData.getString("profileFileName");
                    Resources resources = this.getResources();
                    int resourceId = resources.getIdentifier(posterFileName, "drawable", getPackageName());
                    Movie m = new Movie(movieTitle, releaseDate, director, description, rating, genre, resourceId);
                    return m;
                }
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String loadMoviesFromJSON() {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("Movies.json");
            int fileSize = inputStream.available();
            byte[] bufferData = new byte[fileSize];
            inputStream.read(bufferData);
            json = new String(bufferData, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

}