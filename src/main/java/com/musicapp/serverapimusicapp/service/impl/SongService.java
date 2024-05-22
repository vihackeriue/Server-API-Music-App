package com.musicapp.serverapimusicapp.service.impl;

import com.musicapp.serverapimusicapp.converter.SongConverter;
import com.musicapp.serverapimusicapp.dto.SongDTO;
import com.musicapp.serverapimusicapp.entity.*;
import com.musicapp.serverapimusicapp.repository.ArtistRepository;
import com.musicapp.serverapimusicapp.repository.GenreRepository;
import com.musicapp.serverapimusicapp.repository.SongInteractionsRepository;
import com.musicapp.serverapimusicapp.repository.SongRepository;
import com.musicapp.serverapimusicapp.service.ISongService;
import jakarta.persistence.EntityNotFoundException;
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
    @Autowired
    private SongInteractionsRepository songInteractionsRepository;
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
            songDTO.setUrl_audio("api/song/audio/"+ item.getId());
            songDTO.setUrl_thumbnail("api/song/avatar/"+ item.getId());
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
            songDTO.setUrl_audio("api/song/audio/"+ item.getId());
            songDTO.setUrl_thumbnail("api/song/avatar/"+ item.getId());
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
    public SongDTO findByID(long idsong, long idUser) {
        Optional<SongEntity> optionalSongEntity = songRepository.findById(idsong);
        if(optionalSongEntity.isPresent()){
            SongEntity songEntity = optionalSongEntity.get();
            String urlAudio = songEntity.getUrl_audio();
            SongDTO songDTO = songConverter.toDTO(songEntity);
//            if(idUser != -1){
//                songInteractionsRepository.incrementViews(songEntity.getId(), idUser);
//            }else {
//                updateView(songDTO);
//            }
            incrementViews(idsong, idUser);

            System.out.println(songEntity.getSongInteractions());
            return songDTO;
        }
        return null;
    }
    @Override
    public String findUrlAudioById(Long id) {
        return songRepository.findUrlAudioById(id);
    }
    @Override
    public String findUrlAvatarById(Long id) {
        return songRepository.findUrlAvatarById(id);
    }

    @Override
    public long updateView(SongDTO songDTO) {

        SongEntity songEntity = new SongEntity();
        long views = songDTO.getViews() + 1;
        songDTO.setViews(views);
        songEntity = songConverter.toEntity(songDTO);
        System.out.println(songEntity.getId());
        songRepository.save(songEntity);
        return 0;
    }
    public void incrementViews(Long songId, Long userId) {
        Optional<SongInteractionsEntity> interaction = songInteractionsRepository.findBySongIdAndUserId(songId, userId);
        if (interaction.isPresent()) {
            songInteractionsRepository.incrementViews(songId, userId);
        } else {
            SongInteractionsEntity newInteraction = new SongInteractionsEntity();

            newInteraction.setSong(songRepository.findById(songId).orElseThrow(() -> new EntityNotFoundException("Song not found")));
            UserEntity user = new UserEntity();
            user.setId(userId);
            newInteraction.setUser(user);
            newInteraction.setViews(1);
//            newInteraction.setRating(0); // default value
            System.out.println(newInteraction.getUser().getId());
            songInteractionsRepository.save(newInteraction);
        }
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
