package com.example.cinemacachefinal;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Movie extends AppCompatActivity {
    private String movieTitle;
    private String releaseDate;
    private String director;
    private String description;
    private String rating;
    private String genre;

    /**
     * the resource id for R.drawable.[imageFileName]
     */
    private int posterID;

    public Movie(){}

    public Movie(String movieTitle, String releaseDate, String director, String description, String rating, String genre, int posterID) {
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.director = director;
        this.description = description;
        this.rating = rating;
        this.genre = genre;
        this.posterID = posterID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }


    public String getGenre() {
        return genre;
    }

    public int getPosterID() {
        return posterID;
    }

}
