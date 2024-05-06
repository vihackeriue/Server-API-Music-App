package com.musicapp.serverapimusicapp.dto;

public class ArtistDTO extends BaseDTO<ArtistDTO>{
    private String name;
    private String url_avatar;

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
}
