package com.musicapp.serverapimusicapp.service.impl;

import com.musicapp.serverapimusicapp.converter.GenreConverter;
import com.musicapp.serverapimusicapp.dto.GenreDTO;
import com.musicapp.serverapimusicapp.entity.GenreEntity;
import com.musicapp.serverapimusicapp.repository.GenreRepository;
import com.musicapp.serverapimusicapp.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService implements IGenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreConverter genreConverter;

    @Override
    public GenreDTO save(GenreDTO genreDTO) {
        GenreEntity genreEntity = new GenreEntity();
        if(genreDTO.getId() != null){
            Optional<GenreEntity> optionalGenre = genreRepository.findById(genreDTO.getId());
            if (optionalGenre.isPresent()) {
//                get data before update
                GenreEntity oldDataEntity = optionalGenre.get();
                genreEntity = genreConverter.toEntity(genreDTO,oldDataEntity);
            }else {
            }
        }else {
            genreEntity = genreConverter.toEntity(genreDTO);
        }
        genreEntity = genreRepository.save(genreEntity);
        return genreConverter.toDTO(genreEntity);
    }
    @Override
    public GenreDTO update(GenreDTO genreDTO) {

        Optional<GenreEntity> optionalGenre = genreRepository.findById(genreDTO.getId());
        if (optionalGenre.isPresent()) {
            GenreEntity oldDataEntity = optionalGenre.get();
            GenreEntity newDataEntity = genreConverter.toEntity(genreDTO,oldDataEntity);

            newDataEntity = genreRepository.save(newDataEntity);
            return genreConverter.toDTO(newDataEntity);

        } else {
            return null;
        }
    }
    @Override
    public void delete(long[] ids) {
        for(long item: ids){
            genreRepository.deleteById(item);
        }
    }
    @Override
    public List<GenreDTO> findAll(Pageable pageable) {
        List<GenreDTO> results = new ArrayList<>();
        List<GenreEntity> entities = genreRepository.findAll(pageable).getContent();
        for (GenreEntity item: entities){
            GenreDTO genreDTO = genreConverter.toDTO(item);
            results.add(genreDTO);
        }
        return results;
    }

    @Override
    public List<GenreDTO> findAll() {
        List<GenreDTO> results = new ArrayList<>();
        List<GenreEntity> entities = genreRepository.findAll();
        for (GenreEntity item: entities){
            GenreDTO genreDTO = genreConverter.toDTO(item);
            results.add(genreDTO);
        }
        return results;
    }

    @Override
    public int totalItem() {
        return (int) genreRepository.count();
    }


}
