package com.musicapp.serverapimusicapp.api.input;

import com.musicapp.serverapimusicapp.dto.ArtistDTO;

public class ArtistRequest {
    private String token;
    private ArtistDTO artist;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArtistDTO getArtist() {
        return artist;
    }

    public void setArtist(ArtistDTO artist) {
        this.artist = artist;
    }
}
