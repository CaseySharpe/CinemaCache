package com.example.cinemacachefinal;

import androidx.annotation.NonNull;

import java.security.KeyStore;
import java.time.LocalDate;
import java.util.ArrayList;

public class Review {


    private String review;
    private Movie movie;
    private String rating;

    public Review(String review, Movie movie, String rating) {
        this.review = review;
        this.movie = movie;
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getRating() {
        return rating;
    }


    @Override
    public String toString() {
        return review + "-break-" + movie.getMovieTitle() + "-break-" + rating + "-done-";
    }
}
