package com.musicapp.serverapimusicapp.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity{

    @Column
    private String email;
    @Column
    private String password;

    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "url_avatar")
    private String urlAvatar;

    @Column(name = "user_preferences")
    private String userPreferences;

    @Column
    private Boolean status;

    @OneToMany(mappedBy = "user")
    private List<SongInteractionsEntity> songInteractions;
    @OneToMany(mappedBy = "user")
    private List<ArtistEntity> artists;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }



    public List<SongInteractionsEntity> getSongInteractions() {
        return songInteractions;
    }

    public void setSongInteractions(List<SongInteractionsEntity> songInteractions) {
        this.songInteractions = songInteractions;
    }

    public String getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(String userPreferences) {
        this.userPreferences = userPreferences;
    }
}
