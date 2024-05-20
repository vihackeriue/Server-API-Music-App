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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

//        System.out.println(songDTO.getGenreDTO().getId());
        Optional<GenreEntity> genreOptional = genreRepository.findById(songDTO.getGenreID());
        if (genreOptional.isPresent()) {
            genreEntity = genreOptional.get();
            // Do something with the song object
        } else {
            // Handle case when song with ID 1 is not found
        }
        ArtistEntity artistEntity = artistRepository.findById(songDTO.getArtistID())
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
    @Override
    public SongDTO findByID(long id) {
        Optional<SongEntity> optionalSongEntity = songRepository.findById(id);
        if(optionalSongEntity.isPresent()){
            SongEntity songEntity = optionalSongEntity.get();
            String urlAudio = songEntity.getUrl_audio();
            updateView(songConverter.toDTO(songEntity));
            return songConverter.toDTO(songEntity);
        }
        return null;
    }
    @Override
    public long updateView(SongDTO songDTO) {
        SongEntity songEntity = new SongEntity();
        long views = songDTO.getViews() + 1;
        songDTO.setViews(views);
        songEntity = songConverter.toEntity(songDTO);
        songRepository.save(songEntity);
        return 0;
    }

    @Override
    public String saveFile(MultipartFile file, String url) {
        String songId = UUID.randomUUID().toString();
        // Lưu file với tên là ID của bài hát
        Path path = Paths.get(url + songId + getFileExtension(file.getOriginalFilename()));
        // Lưu file trên server
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }
}
