package com.example.cinemacachefinal;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private HashMap<String, Integer> genreOptions;
    private ArrayList<Movie> movieList;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.user = getUserFromUsername("sampleuser");
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
        findViewById(R.id.add_to_watchlist_main).setOnClickListener(this);
    }

    private void updateMovieProfile() {
        boolean validMovie = false;
        while (!validMovie) {
            int index = (int) (Math.random() * movieList.size());
            Movie m = movieList.get(index);
            CheckBox c = findViewById(genreOptions.get(m.getGenre()));
            if (c.isChecked()) {
                showMovieProfile(m);
                validMovie = true;
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
        if (id == R.id.add_to_watchlist_main) {
            TextView movieTitle = findViewById(R.id.movie_title);
            if(!this.user.getUserWatchlist().contains(movieTitle.getText().toString())){
                this.user.addToUserWatchlist(movieTitle.getText().toString());
            }
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
            return true;
        }
        else if (item.getItemId() == R.id.navigation_watchlist) {
            Intent movieListIntent = new Intent(this, WatchListActivity.class);
            movieListIntent.putExtra("username", user.getUsername());
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


    private String loadUserFromJSON() {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("Users.json");
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

    public User getUserFromUsername(String username) {
        try {
            JSONObject jsonObject = new JSONObject(loadUserFromJSON());
            JSONArray jsonArray = jsonObject.getJSONArray("users");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userData = jsonArray.getJSONObject(i);
                String dataUsername = userData.getString("username");
                if (username.equals(dataUsername)) {
                    String password = userData.getString("password");
                    JSONArray userWatchList = userData.getJSONArray("userWatchList");
                    ArrayList<String> userWatchListArray = new ArrayList<>();
                    for (int j = 0; j < userWatchList.length(); j++){
                        userWatchListArray.add(userWatchList.getString(j));
                    }
                    User u = new User(username, password, userWatchListArray);
                    return u;
                }
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
