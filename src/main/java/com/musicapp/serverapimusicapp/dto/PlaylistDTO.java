package com.musicapp.serverapimusicapp.dto;


import com.musicapp.serverapimusicapp.entity.SongEntity;

import java.util.List;

public class PlaylistDTO extends BaseDTO<PlaylistDTO>{

    private String name;
    private String description;
    private String urlAvatar;

    private List<Long> songIds;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<Long> songIds) {
        this.songIds = songIds;
    }
}
