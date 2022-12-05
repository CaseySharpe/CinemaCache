package com.example.cinemacachefinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewListViewHolder> {
    private final ArrayList<Review> mReviewList;
    private final LayoutInflater mInflater;
    private final Context context;

    public ReviewListAdapter(Context context, ArrayList<Review> mReviewList) {
        this.context = context;
        this.mReviewList = mReviewList;
        mInflater = LayoutInflater.from(context);
    }


    public ReviewListAdapter.ReviewListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.movie_list_item,
                parent, false);
        return new ReviewListAdapter.ReviewListViewHolder(mItemView, this);
    }

    public void onBindViewHolder(@NonNull ReviewListViewHolder holder, int position) {
        Review reviewData = mReviewList.get(position);
        String movieTitle = reviewData.getMovie().getMovieTitle();
        String description = reviewData.getReview();
        String rating = reviewData.getRating();
        int posterID = reviewData.getMovie().getPosterID();
        holder.movieTitleView.setText(movieTitle);
        holder.movieGenreView.setText("");
        if (rating.equals("Four Stars")){
            holder.starFiveView.setVisibility(View.INVISIBLE);
        }
        else if (rating.equals("Three Stars")){
            holder.starFiveView.setVisibility(View.INVISIBLE);
            holder.starFourView.setVisibility(View.INVISIBLE);
        }
        else if (rating.equals("Two Stars")){
            holder.starFiveView.setVisibility(View.INVISIBLE);
            holder.starFourView.setVisibility(View.INVISIBLE);
            holder.starThreeView.setVisibility(View.INVISIBLE);
        }
        else if (rating.equals("Two Stars")){
            holder.starFiveView.setVisibility(View.INVISIBLE);
            holder.starFourView.setVisibility(View.INVISIBLE);
            holder.starThreeView.setVisibility(View.INVISIBLE);
            holder.starTwoView.setVisibility(View.INVISIBLE);
        }
        holder.movieDescriptionView.setText("Review: " + description);
        holder.moviePosterView.setImageResource(posterID);
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    class ReviewListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView movieTitleView;
        public final ImageView moviePosterView;
        public final TextView movieDescriptionView;
        public final TextView movieGenreView;
        public final ImageView starOneView;
        public final ImageView starTwoView;
        public final ImageView starThreeView;
        public final ImageView starFourView;
        public final ImageView starFiveView;
        final ReviewListAdapter mAdapter;


        public ReviewListViewHolder(@NonNull View itemView, ReviewListAdapter adapter) {
            super(itemView);
            movieTitleView = itemView.findViewById(R.id.movie_list_title);
            moviePosterView = itemView.findViewById(R.id.movie_poster);
            movieDescriptionView = itemView.findViewById(R.id.movie_list_description);
            movieGenreView = itemView.findViewById(R.id.movie_list_genre);
            starOneView = itemView.findViewById(R.id.star_1);
            starTwoView = itemView.findViewById(R.id.star_2);
            starThreeView = itemView.findViewById(R.id.star_3);
            starFourView = itemView.findViewById(R.id.star_4);
            starFiveView = itemView.findViewById(R.id.star_5);
            //set movie on click listener
            movieTitleView.setOnClickListener(this);
            this.mAdapter = adapter;
        }


        @Override
        public void onClick(View view) {
            //gets the title of the movie clicked;
            int id = view.getId();
            if (id == R.id.movie_list_title) {
                String movieTitle = movieTitleView.getText().toString();
                Intent myIntent = new Intent(context, DetailViewActivity.class);
                //sends title of the clicked movie to display movie clicked to detail page activity
                myIntent.putExtra("movie_title", movieTitle);
                context.startActivity(myIntent);
            }
        }

    }
}
