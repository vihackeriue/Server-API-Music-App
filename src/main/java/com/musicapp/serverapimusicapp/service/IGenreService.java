package com.musicapp.serverapimusicapp.service;

import com.musicapp.serverapimusicapp.dto.GenreDTO;
import com.musicapp.serverapimusicapp.entity.GenreEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGenreService {

    GenreDTO save(GenreDTO genreDTO);
    GenreDTO update(GenreDTO genreDTO);
    void delete(long[] ids);
    List<GenreDTO> findAll(Pageable pageable);
    List<GenreDTO> findAll();
    int totalItem();


}
