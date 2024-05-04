package com.musicapp.serverapimusicapp.service.impl;

import com.musicapp.serverapimusicapp.converter.GenreConverter;
import com.musicapp.serverapimusicapp.dto.GenreDTO;
import com.musicapp.serverapimusicapp.entity.GenreEntity;
import com.musicapp.serverapimusicapp.repository.GenreRepository;
import com.musicapp.serverapimusicapp.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService implements IGenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreConverter genreConverter;

    @Override
    public GenreDTO save(GenreDTO genreDTO) {

        GenreEntity genreEntity = genreConverter.toEntity(genreDTO);
        genreEntity = genreRepository.save(genreEntity);
        return genreConverter.toDTO(genreEntity);
    }
}
