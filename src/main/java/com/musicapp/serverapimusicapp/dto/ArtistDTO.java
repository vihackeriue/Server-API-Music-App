package com.musicapp.serverapimusicapp.dto;

import java.util.List;

public class ArtistDTO extends BaseDTO<ArtistDTO>{
    private String name;
    private String url_avatar;
    private List<Long> songIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl_avatar() {
        return url_avatar;
    }

    public void setUrl_avatar(String url_avatar) {
        this.url_avatar = url_avatar;
    }

    public List<Long> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<Long> songIds) {
        this.songIds = songIds;
    }
}
