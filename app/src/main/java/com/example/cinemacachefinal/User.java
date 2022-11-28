package com.example.cinemacachefinal;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;

    /**
     * holds an arraylist of movie titles
     */
    private ArrayList<String> userWatchlist;

    public User(String username, String password, ArrayList<String> userWatchlist) {
        this.username = username;
        this.password = password;
        this.userWatchlist = userWatchlist;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.userWatchlist = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getUserWatchlist() {
        return userWatchlist;
    }

    public void addToUserWatchlist(String movieTitle) {
        this.userWatchlist.add(movieTitle);
    }


}
