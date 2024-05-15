package com.musicapp.serverapimusicapp.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "song_interactions")
public class SongInteractionsEntity extends BaseEntity{


    @Column(name = "interaction_type")
    private String interactionType;
    @Column()
    private String rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private SongEntity song;

    public String getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(String interactionType) {
        this.interactionType = interactionType;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
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
}
