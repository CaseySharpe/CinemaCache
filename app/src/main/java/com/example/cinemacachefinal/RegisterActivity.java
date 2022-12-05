package com.example.cinemacachefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{


    private static final String TAG = "RegisterActivity";


    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREF_NAME = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.cancel_registration_button).setOnClickListener(this);
        findViewById(R.id.register_button).setOnClickListener(this);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
    }


    @Override
    public void onClick(View view){
        int id = view.getId();
        ArrayList<EditText> textFields = new ArrayList<>();
        EditText firstNameField = findViewById(R.id.editFirstName);
        textFields.add(firstNameField);
        EditText lastNameField = findViewById(R.id.editLastName);
        textFields.add(lastNameField);
        EditText emailField = findViewById(R.id.editTextEmail);
        textFields.add(emailField);
        EditText passwordField = findViewById(R.id.editTextPassword);
        textFields.add(passwordField);
        if(id == R.id.cancel_registration_button){
            finish();
        }
        else if(id == R.id.register_button){
            boolean emptyFields = false;
            for(EditText textField : textFields){
                if(textField.getText().toString().isEmpty()){
                    emptyFields = true;
                    AlertDialog.Builder d = new AlertDialog.Builder(this);
                    d.setTitle(R.string.register_error_title);
                    d.setMessage(R.string.register_error_message);
                    d.setPositiveButton(android.R.string.ok, null);
                    d.show();
                }
            }
            if(!emptyFields){
                Random r = new Random();
                saveUserInformation(firstNameField.getText().toString(), lastNameField.getText().toString(), emailField.getText().toString(), passwordField.getText().toString());
                Button button = findViewById(R.id.cancel_registration_button);
                Snackbar.make(button, R.string.register_complete, Snackbar.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public void saveUserInformation(String firstName, String lastName, String email, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("EMAIL", email);
        editor.putString("PASSWORD", password);
        editor.putString("FIRSTNAME", firstName);
        editor.putString("LASTNAME", lastName);
        editor.putString("WATCHLIST", "");
        editor.putString("REVIEWS", "");
        editor.apply();
    }




}
