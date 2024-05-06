package com.musicapp.serverapimusicapp.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genre")
public class GenreEntity extends BaseEntity{
    @Column
    private String name;
    @Column
    private String code;

    @OneToMany(mappedBy = "genre")
    private List<SongEntity> songs = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
