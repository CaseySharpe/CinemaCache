package com.example.cinemacachefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class DetailViewActivity extends AppCompatActivity implements View.OnClickListener  {

    String selectedMovieExtra;
    Movie selectedMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        selectedMovieExtra = getIntent().getStringExtra("movie_title");
        selectedMovie = getSelectedMovie();

        TextView movieTitleView = findViewById(R.id.detail_movie_title);
        ImageView moviePosterView = findViewById(R.id.detail_movie_poster);
        TextView movieDescriptionView = findViewById(R.id.detail_movie_description);
        TextView movieGenreView = findViewById(R.id.detail_movie_genre);
        TextView movieRatingView = findViewById(R.id.detail_movie_rating);
        movieTitleView.setText(selectedMovie.getMovieTitle());
        movieDescriptionView.setText(selectedMovie.getDescription());
        movieGenreView.setText(selectedMovie.getGenre());
        movieRatingView.setText(selectedMovie.getRating());
        moviePosterView.setImageResource(selectedMovie.getPosterID());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.navigation_top_movies) {
            Intent movieListIntent = new Intent(this, TopMovieListActivity.class);
            startActivity(movieListIntent);
            return true;
        }
        else if (item.getItemId() == R.id.navigation_find_movies) {
            Intent movieListIntent = new Intent(this, MainActivity.class);
            startActivity(movieListIntent);
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
        if (id == R.id.add_to_watchlist_main) {
            Intent movieListIntent = new Intent(this, TopMovieListActivity.class);
            startActivity(movieListIntent);
            finish();
        }
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