package com.example.cinemacachefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private static final String TAG = "LoginActivity";

    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREF_NAME = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(
                SHARED_PREF_NAME,
                MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.button_cancel_login).setOnClickListener(this);
        findViewById(R.id.button_login).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.navigation_register) {
            Intent movieListIntent = new Intent(this, RegisterActivity.class);
            startActivity(movieListIntent);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view){
        int id = view.getId();
        EditText userNameField = findViewById(R.id.editTextEmail);
        EditText passwordField = findViewById(R.id.editTextPassword);
        if(id == R.id.button_cancel_login){
            Log.d(TAG, "Cancel button clicked");
            userNameField.setText("");
            passwordField.setText("");
        }
        else if(id == R.id.button_login){
            Button button = findViewById(R.id.button_cancel_login);
            String passwordData = sharedPreferences.getString("PASSWORD", null);
            String emailData = sharedPreferences.getString("EMAIL", null);
            System.out.println(passwordData);
            System.out.println(emailData);
            if (userNameField.getText().toString().equals(emailData) && passwordField.getText().toString().equals(passwordData)){
                Snackbar.make(button, R.string.login_complete, Snackbar.LENGTH_LONG).show();
                Intent findMoviesIntent = new Intent(this, MainActivity.class);
                startActivity(findMoviesIntent);
                finish();
            }
            else{
                //TODO - Need to validate that fields are filled in
                AlertDialog.Builder d = new AlertDialog.Builder(this);
                d.setTitle(R.string.login_error_title);
                d.setMessage(R.string.login_error_message);
                d.setPositiveButton(android.R.string.ok, null);
                d.show();
            }
        }
    }


}