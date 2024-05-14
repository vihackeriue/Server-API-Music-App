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
    private UserEntity users;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private SongEntity songs;

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

    public UserEntity getUsers() {
        return users;
    }

    public void setUsers(UserEntity users) {
        this.users = users;
    }

    public SongEntity getSongs() {
        return songs;
    }

    public void setSongs(SongEntity songs) {
        this.songs = songs;
    }
}
