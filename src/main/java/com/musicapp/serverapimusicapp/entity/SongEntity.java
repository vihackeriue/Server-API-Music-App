package com.musicapp.serverapimusicapp.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "song")
public class SongEntity extends BaseEntity{
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String url_thumbnail;
    @Column
    private String url_audio;
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "song_playlist", joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id"))
    private List<PlaylistEntity> playlist = new ArrayList<>();

    public GenreEntity getGenre() {
        return genre;
    }

    public void setGenre(GenreEntity genre) {
        this.genre = genre;
    }

    public ArtistEntity getArtist() {
        return artist;
    }

    public void setArtist(ArtistEntity artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_thumbnail() {
        return url_thumbnail;
    }

    public void setUrl_thumbnail(String url_thumbnail) {
        this.url_thumbnail = url_thumbnail;
    }

    public String getUrl_audio() {
        return url_audio;
    }

    public void setUrl_audio(String url_audio) {
        this.url_audio = url_audio;
    }

    public List<PlaylistEntity> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<PlaylistEntity> playlist) {
        this.playlist = playlist;
    }
}
