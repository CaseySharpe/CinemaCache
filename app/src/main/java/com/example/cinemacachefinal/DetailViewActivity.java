package com.example.cinemacachefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class DetailViewActivity extends AppCompatActivity implements View.OnClickListener  {

    String selectedMovieExtra;
    Movie selectedMovie;


    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREF_NAME = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
            sharedPreferences = getSharedPreferences(
                    SHARED_PREF_NAME,
                    MODE_PRIVATE);
        selectedMovieExtra = getIntent().getStringExtra("movie_title");
        selectedMovie = getSelectedMovie();

        TextView movieTitleView = findViewById(R.id.detail_movie_title);
        ImageView moviePosterView = findViewById(R.id.detail_movie_poster);
        TextView movieDescriptionView = findViewById(R.id.detail_movie_description);
        TextView movieDirectorView = findViewById(R.id.detail_movie_director);
        TextView movieGenreView = findViewById(R.id.detail_movie_genre);
        TextView movieRatingView = findViewById(R.id.detail_movie_rating);
        moviePosterView.setImageResource(selectedMovie.getPosterID());
        movieTitleView.setText(selectedMovie.getMovieTitle());
        movieDirectorView.setText("Director: " + selectedMovie.getDirector());
        movieDescriptionView.setText("Description: " + selectedMovie.getDescription());
        movieGenreView.setText("Genre: " + selectedMovie.getGenre());
        movieRatingView.setText("Rating: " + selectedMovie.getRating());
        findViewById(R.id.review_button).setOnClickListener(this);
        findViewById(R.id.back_button_details).setOnClickListener(this);
        findViewById(R.id.add_to_watchlist).setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.add_to_watchlist) {
            addMovieToWatchList();
        }
        else if (id == R.id.review_button) {
            Intent reviewIntent = new Intent(this, ReviewActivity.class);
            reviewIntent.putExtra("movie_title", selectedMovie.getMovieTitle());
            startActivity(reviewIntent);
            finish();
        }
        else if (id == R.id.back_button_details){
            finish();
        }
    }

    private void addMovieToWatchList() {
        TextView movieTitle = findViewById(R.id.detail_movie_title);
        String watchlist = sharedPreferences.getString("WATCHLIST", null);
        String[] watchlistList = watchlist.split("-break-");
        String newWatchList = "";
        Boolean alreadyInWatchList = false;
        for (String title : watchlistList) {
            System.out.println(title);
            if(title.equals(movieTitle.getText().toString())) {
                Button button = findViewById(R.id.back_button_details);
                Snackbar.make(button, R.string.already_in_watchlist_snackbar, Snackbar.LENGTH_LONG).show();
                newWatchList = watchlist;
                alreadyInWatchList = true;
            }
        }
        if(!alreadyInWatchList){
            newWatchList = watchlist + movieTitle.getText().toString() + "-break-";
            Button button = findViewById(R.id.back_button_details);
            Snackbar.make(button, R.string.add_watchlist_message, Snackbar.LENGTH_LONG).show();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("WATCHLIST", newWatchList);
        editor.apply();
        System.out.println(sharedPreferences.getString("WATCHLIST", null));
    }

    public Movie getSelectedMovie() {
        try {
            JSONObject jsonObject = new JSONObject(loadMoviesFromJSON());
            JSONArray jsonArray = jsonObject.getJSONArray("movies");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieData = jsonArray.getJSONObject(i);
                String movieTitle = movieData.getString("movieTitle");
                if(movieTitle.equals(selectedMovieExtra)){
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