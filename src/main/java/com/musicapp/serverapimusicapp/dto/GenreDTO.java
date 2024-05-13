package com.musicapp.serverapimusicapp.dto;

import java.util.List;

public class GenreDTO extends BaseDTO<GenreDTO> {
    private String name;
    private String code;
    private List<Long> songIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Long> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<Long> songIds) {
        this.songIds = songIds;
    }
}
