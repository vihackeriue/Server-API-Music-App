package com.musicapp.serverapimusicapp.dto;



public class SongInteractionsDTO extends BaseDTO<SongInteractionsDTO>{





    private int rating;
    private Long userID;
    private Long songID;
    private long views;




    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getSongID() {
        return songID;
    }

    public void setSongID(Long songID) {
        this.songID = songID;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }
}
