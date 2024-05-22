package com.musicapp.serverapimusicapp.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "song_interactions")
public class SongInteractionsEntity extends BaseEntity{
    @Column()
    private int rating;
    @Column()
    private long views;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public SongEntity getSong() {
        return song;
    }

    public void setSong(SongEntity song) {
        this.song = song;
    }



    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }
}
