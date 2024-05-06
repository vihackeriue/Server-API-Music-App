package com.musicapp.serverapimusicapp.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "artist")
public class ArtistEntity extends BaseEntity{
    @Column
    private String name;
    @Column(name = "urlAvatar")
    private String urlAvatar;
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<SongEntity> songs = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public List<SongEntity> getSongs() {
        return songs;
    }

    public void setSongs(List<SongEntity> songs) {
        this.songs = songs;
    }
}
