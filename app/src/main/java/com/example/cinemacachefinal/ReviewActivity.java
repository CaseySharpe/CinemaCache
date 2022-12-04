package com.example.cinemacachefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class ReviewActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
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
}