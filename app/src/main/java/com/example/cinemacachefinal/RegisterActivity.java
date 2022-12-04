package com.example.cinemacachefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{


    private static final String TAG = "RegisterActivity";
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.button_register).setOnClickListener(this);
        findViewById(R.id.button_cancel_registration).setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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
    public void onClick(View view){
        int id = view.getId();

        if(id == R.id.button_cancel_registration){
            Log.d(TAG, "Cancel button clicked");
            finish();
        }
        else if(id == R.id.button_register){

            Button button = findViewById(R.id.button_register);
            Snackbar.make(button, R.string.register_complete, Snackbar.LENGTH_LONG).show();


        } else{
            //TODO - Need to validate that fields are filled in
            AlertDialog.Builder d = new AlertDialog.Builder(this);
            d.setTitle(R.string.register_error_title);
            d.setMessage(R.string.register_error_message);
            d.setPositiveButton(android.R.string.ok, null);
            d.show();
        }
    }




}
