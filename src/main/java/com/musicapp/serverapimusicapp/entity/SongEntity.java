package com.musicapp.serverapimusicapp.entity;

import jakarta.persistence.Column;

public class SongEntity extends BaseEntity{
    @Column
    private String name;
    @Column
    private String describtion;
    @Column
    private String url_thumbnail;
    @Column
    private String url_audio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
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
}
