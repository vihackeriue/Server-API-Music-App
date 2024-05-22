package com.musicapp.serverapimusicapp.service.impl;

import com.musicapp.serverapimusicapp.converter.SongInteractionsConverter;
import com.musicapp.serverapimusicapp.dto.SongInteractionsDTO;
import com.musicapp.serverapimusicapp.entity.GenreEntity;
import com.musicapp.serverapimusicapp.entity.SongEntity;
import com.musicapp.serverapimusicapp.entity.SongInteractionsEntity;
import com.musicapp.serverapimusicapp.entity.UserEntity;
import com.musicapp.serverapimusicapp.repository.SongInteractionsRepository;
import com.musicapp.serverapimusicapp.repository.SongRepository;
import com.musicapp.serverapimusicapp.repository.UserRepository;
import com.musicapp.serverapimusicapp.service.ISongInteractionsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongInteractionsService implements ISongInteractionsService {
    @Autowired
    private SongInteractionsConverter songInteractionsConverter;
    @Autowired
    private SongInteractionsRepository songInteractionsRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public SongInteractionsDTO save(SongInteractionsDTO songInteractionsDTO) {
        SongInteractionsEntity entity = songInteractionsConverter.toEntity(songInteractionsDTO);

        Optional<SongEntity> songOptional = songRepository.findById(songInteractionsDTO.getSongID());
        if (songOptional.isPresent()) {
            entity.setSong(songOptional.get());
            // Do something with the song object
        } else {
            return null;
            // Handle case when song with ID 1 is not found
        }
        Optional<UserEntity> userOptional = userRepository.findById(songInteractionsDTO.getUserID());
        if (songOptional.isPresent()) {
            entity.setUser(userOptional.get());
            // Do something with the song object
        } else {
            return null;
            // Handle case when song with ID 1 is not found
        }
        Optional<SongInteractionsEntity> interaction = songInteractionsRepository.findBySongIdAndUserId(songInteractionsDTO.getSongID(), songInteractionsDTO.getUserID());
        if (interaction.isPresent()) {
            songInteractionsRepository.updateRating(songInteractionsDTO.getSongID(), songInteractionsDTO.getUserID(),songInteractionsDTO.getRating());
        }else {
            entity = songInteractionsRepository.save(entity);

        }
        return songInteractionsConverter.toDTO(entity);
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
    public List<SongInteractionsDTO> findByIdSong(Long idSong) {
        List<SongInteractionsEntity> songInteractionsEntity = songInteractionsRepository.findByIDSong(idSong);
        List<SongInteractionsDTO> result = new ArrayList<>();
        for (SongInteractionsEntity item : songInteractionsEntity){
            result.add(songInteractionsConverter.toDTO(item));
        }
        return result;
    }
}
