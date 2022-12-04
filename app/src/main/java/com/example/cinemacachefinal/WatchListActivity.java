package com.example.cinemacachefinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class WatchListActivity extends AppCompatActivity {

        private RecyclerView recyclerView;
        private MovieListAdapter movieListAdapter;
        private String usernameExtra;
        private ArrayList<String> userWatchList;
        private User user;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_watch_list);
            usernameExtra = getIntent().getStringExtra("username");
            userWatchList = getUserWatchlist(usernameExtra);

            recyclerView = findViewById(R.id.recycler_watchlist);
            movieListAdapter = new MovieListAdapter(this, getMovieList());
            recyclerView.setAdapter(movieListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
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
        else if(item.getItemId() == R.id.navigation_register){
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);

            return true;
        }
        else if(item.getItemId() == R.id.navigation_reviews){
            Intent reviewIntent = new Intent(this, ReviewActivity.class);
            startActivity(reviewIntent);

            return true;
        }
        else if(item.getItemId() == R.id.navigation_find_movies){
            Intent findMoviesIntent = new Intent(this, MainActivity.class);
            startActivity(findMoviesIntent);

            return true;
        }
        else if(item.getItemId() == R.id.navigation_login){
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);

            return true;
        }

        return false;
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return true;
        }


        /**
         * Getting the data from json file
         */
        public ArrayList<Movie> getMovieList() {
            ArrayList<Movie> movieArrayList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(loadMoviesFromJSON());
                JSONArray jsonArray = jsonObject.getJSONArray("movies");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject movieData = jsonArray.getJSONObject(i);
                    String movieTitle = movieData.getString("movieTitle");
                    if (userWatchList.contains(movieTitle)) {
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
                }
            } catch (
                    JSONException e) {
                e.printStackTrace();
            }
            return movieArrayList;
        }

        public ArrayList<String> getUserWatchlist(String usernameExtra) {
            ArrayList<String> userWatchListArray = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(loadUserFromJSON());
                JSONArray jsonArray = jsonObject.getJSONArray("users");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject userData = jsonArray.getJSONObject(i);
                    String username = userData.getString("username");
                    if (usernameExtra.equals(username)) {
                        JSONArray userWatchList = userData.getJSONArray("userWatchList");
                        for (int j = 0; j < userWatchList.length(); j++){
                            userWatchListArray.add(userWatchList.getString(j));
                        }
                    }
                }
            } catch (
                    JSONException e) {
                e.printStackTrace();
            }
            return userWatchListArray;
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
    }
