package com.example.cinemacachefinal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private final ArrayList<Movie> mMovieList;
    private final LayoutInflater mInflater;
    private final Context context;

    public MovieListAdapter(Context context, ArrayList<Movie> mMovieList) {
        this.context = context;
        this.mMovieList = mMovieList;
        mInflater = LayoutInflater.from(context);
    }


    public MovieListAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.movie_list_item,
                parent, false);
        return new MovieViewHolder(mItemView, this);
    }

    public void onBindViewHolder(@NonNull MovieListAdapter.MovieViewHolder holder, int position) {
        Movie movieData = mMovieList.get(position);
        String movieTitle = movieData.getMovieTitle();
        String description = movieData.getDescription();
        String rating = movieData.getRating();
        String genre = movieData.getGenre();
        int posterID = movieData.getPosterID();
        holder.movieTitleView.setText(movieTitle);
        holder.movieGenreView.setText(genre);
        holder.movieDescriptionView.setText(description);
        holder.movieRatingView.setText(rating);
        holder.moviePosterView.setImageResource(posterID);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView movieTitleView;
        public final ImageView moviePosterView;
        public final TextView movieDescriptionView;
        public final TextView movieGenreView;
        public final TextView movieRatingView;
        final MovieListAdapter mAdapter;


        public MovieViewHolder(@NonNull View itemView, MovieListAdapter adapter) {
            super(itemView);
            movieTitleView = itemView.findViewById(R.id.movie_list_title);
            moviePosterView = itemView.findViewById(R.id.movie_poster);
            movieDescriptionView = itemView.findViewById(R.id.movie_list_description);
            movieGenreView = itemView.findViewById(R.id.movie_list_genre);
            movieRatingView = itemView.findViewById(R.id.movie_list_rating);
            //set movie on click listener
            movieTitleView.setOnClickListener(this);
            this.mAdapter = adapter;
        }


        @Override
        public void onClick(View view) {
                //gets the title of the movie clicked
                TextView title = view.findViewById(R.id.movie_list_title);
                String movieTitle = title.getText().toString();

                Intent myIntent = new Intent(context, DetailViewActivity.class);
                //sends title of the clicked movie to display movie clicked to detail page activity
                myIntent.putExtra("movie_title", movieTitle);
                context.startActivity(myIntent);
            }
        }
    }


