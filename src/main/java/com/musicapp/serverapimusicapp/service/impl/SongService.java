package com.musicapp.serverapimusicapp.service.impl;

import com.musicapp.serverapimusicapp.converter.SongConverter;
import com.musicapp.serverapimusicapp.dto.SongDTO;
import com.musicapp.serverapimusicapp.entity.ArtistEntity;
import com.musicapp.serverapimusicapp.entity.GenreEntity;
import com.musicapp.serverapimusicapp.entity.SongEntity;
import com.musicapp.serverapimusicapp.repository.ArtistRepository;
import com.musicapp.serverapimusicapp.repository.GenreRepository;
import com.musicapp.serverapimusicapp.repository.SongRepository;
import com.musicapp.serverapimusicapp.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongService implements ISongService {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private SongConverter songConverter;
    @Override
    public SongDTO save(SongDTO songDTO) {
        SongEntity songEntity = new SongEntity();
        if(songDTO.getId() != null){
            Optional<SongEntity> optionalGenre = songRepository.findById(songDTO.getId());
            if (optionalGenre.isPresent()) {
//                get data before update
                SongEntity oldDataEntity = optionalGenre.get();
                songEntity = songConverter.toEntity(songDTO,oldDataEntity);
            }else {
            }
        }else {
            songEntity = songConverter.toEntity(songDTO);
        }
//        lấy genre của bài hát
        GenreEntity genreEntity = new GenreEntity();
        Optional<GenreEntity> genreOptional = genreRepository.findById(songDTO.getGenreDTO().getId());
        if (genreOptional.isPresent()) {
            genreEntity = genreOptional.get();
            // Do something with the song object
        } else {
            // Handle case when song with ID 1 is not found
        }
        ArtistEntity artistEntity = artistRepository.findById(songDTO.getArtistDTO().getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nghệ sĩ với ID: " + songDTO.getArtistDTO().getId()));
        songEntity.setGenre(genreEntity);
        songEntity.setArtist(artistEntity);
//        save in database
        songEntity = songRepository.save(songEntity);

        return songConverter.toDTO(songEntity);
    }

    @Override
    public List<SongDTO> findAll(Pageable pageable) {
        List<SongDTO> results = new ArrayList<>();
        List<SongEntity> entities = songRepository.findAll(pageable).getContent();
        for(SongEntity item: entities){
            SongDTO songDTO = songConverter.toDTO(item);
            results.add(songDTO);
        }
        return results;
    }

    @Override
    public List<SongDTO> findAll() {
        List<SongDTO> results = new ArrayList<>();
        List<SongEntity> entities = songRepository.findAll();
        for(SongEntity item: entities){
            SongDTO songDTO = songConverter.toDTO(item);
            results.add(songDTO);
        }
        return results;
    }

    @Override
    public int totalItem() {
        return (int) songRepository.count();
    }
    @Override
    public void delete(long[] ids) {
        for(long item: ids){
            songRepository.deleteById(item);
        }
    }
}
