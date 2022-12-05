package com.example.cinemacachefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private String selectedMovieExtra;
    private Movie selectedMovie;

    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREF_NAME = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        sharedPreferences = getSharedPreferences(
                SHARED_PREF_NAME,
                MODE_PRIVATE);

        selectedMovieExtra = getIntent().getStringExtra("movie_title");
        selectedMovie = getSelectedMovie();
        Spinner spinner = (Spinner) findViewById(R.id.rating_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.star_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        TextView movieTitleView = findViewById(R.id.movie_title);
        ImageView moviePosterView = findViewById(R.id.moviePoster);
        movieTitleView.setText(selectedMovie.getMovieTitle());
        moviePosterView.setImageResource(selectedMovie.getPosterID());

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.back_button) {
            Intent movieListIntent = new Intent(this, DetailViewActivity.class);
            movieListIntent.putExtra("movie_title", selectedMovie.getMovieTitle());
            startActivity(movieListIntent);
            finish();
        }
        else if (id == R.id.review_button) {
            String newReview = createNewReview().toString();
            String userReviews = sharedPreferences.getString("REVIEWS", null);
            String addedReview = userReviews + newReview;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("REVIEWS", addedReview);
            editor.apply();
            Intent movieListIntent = new Intent(this, ReviewListActivity.class);
            startActivity(movieListIntent);
            finish();
        }
    }

    public Review createNewReview(){
        Spinner spinner = (Spinner) findViewById(R.id.rating_spinner);
        EditText textBox = (EditText) findViewById(R.id.review_field);
        String selected = spinner.getSelectedItem().toString();
        String review = textBox.getText().toString();
        if (review.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error Creating New Review");
            builder.setMessage(R.string.dialog_message);
            builder.setPositiveButton("OK", null);
            builder.show();
        }
        Review newReview = new Review(review, selectedMovie, selected);
        return newReview;
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