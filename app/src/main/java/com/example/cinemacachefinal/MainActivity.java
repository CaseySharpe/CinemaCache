package com.example.cinemacachefinal;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private HashMap<String, Integer> genreOptions;
    private ArrayList<Movie> movieList;

    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREF_NAME = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(
                SHARED_PREF_NAME,
                MODE_PRIVATE);

        genreOptions = new HashMap<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.movieList = getMovieList();
        genreOptions.put("Action", R.id.action_button);
        genreOptions.put("Comedy", R.id.comedy_button);
        genreOptions.put("Horror", R.id.horror_button);
        genreOptions.put("Romance", R.id.romance_button);
        genreOptions.put("Scifi", R.id.scifi_button);
        genreOptions.put("Drama", R.id.drama_button);
        for (int id : genreOptions.values()) {
            CheckBox c = findViewById(id);
            c.setChecked(true);
        }
        findViewById(R.id.shuffle_button).setOnClickListener(this);
        findViewById(R.id.back_button_details).setOnClickListener(this);
    }

    private void addMovieToWatchList() {
        TextView movieTitle = findViewById(R.id.movie_title);
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

    private void updateMovieProfile() {
        boolean validMovie = false;
        int loopCount = 0;
        while (!validMovie) {
            int index = (int) (Math.random() * movieList.size());
            Movie m = movieList.get(index);
            CheckBox c = findViewById(genreOptions.get(m.getGenre()));
            if (c.isChecked()) {
                showMovieProfile(m);
                validMovie = true;
            }
            loopCount++;
            if (loopCount > 20) {
                validMovie = true;
                AlertDialog.Builder d = new AlertDialog.Builder(this);
                d.setTitle("Unable to shuffle");
                d.setMessage("Please check that you have a genre selected and try again.");
                d.setPositiveButton(android.R.string.ok, null);
                d.show();
            }
        }
    }

    private void showMovieProfile(Movie m) {
        ShapeableImageView movieImage = findViewById(R.id.moviePoster);
        movieImage.setImageResource(m.getPosterID());

        TextView view;
        view = findViewById(R.id.movie_title);
        view.setText(m.getMovieTitle());

    }

    public ArrayList<Movie> getMovieList() {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(loadMoviesFromJSON());
            JSONArray jsonArray = jsonObject.getJSONArray("movies");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieData = jsonArray.getJSONObject(i);
                String movieTitle = movieData.getString("movieTitle");
                String releaseDate = movieData.getString("releaseDate");
                String director = movieData.getString("director");
                String description = movieData.getString("description");
                String rating = movieData.getString("rating");
                String genre = movieData.getString("genre");
                String posterFileName = movieData.getString("profileFileName");
                Resources resources = this.getResources();
                int resourceId = resources.getIdentifier(posterFileName, "drawable", getPackageName());
                Movie m = new Movie(movieTitle, releaseDate, director, description, rating, genre, resourceId);
                movieArrayList.add(m);
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        return movieArrayList;
    }

    private String loadMoviesFromJSON() {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("Movies.json");
            int fileSize = inputStream.available();
            byte[] bufferData = new byte[fileSize];
            inputStream.read(bufferData);
            json = new String(bufferData, "UTF-8");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.shuffle_button) {
            updateMovieProfile();
        }
        if (id == R.id.back_button_details) {
            addMovieToWatchList();
        }
        //TODO send to details page when movie poster is clicked
//        else if (id == R.id.moviePoster){
//            TextView view;
//            view = findViewById(R.id.movie_title);
//            TextView title = view.findViewById(R.id.movie_title);
//            String movieTitle = title.getText().toString();
//            System.out.println(movieTitle);
//            Intent myIntent = new Intent(this, DetailViewActivity.class);
//            myIntent.putExtra("movie_title", movieTitle);
//            startActivity(myIntent);
//        }
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




}
