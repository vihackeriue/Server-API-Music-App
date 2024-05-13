package com.musicapp.serverapimusicapp.service.impl;

import com.musicapp.serverapimusicapp.converter.PlaylistConverter;
import com.musicapp.serverapimusicapp.dto.PlaylistDTO;
import com.musicapp.serverapimusicapp.entity.ArtistEntity;
import com.musicapp.serverapimusicapp.entity.PlaylistEntity;
import com.musicapp.serverapimusicapp.entity.SongEntity;
import com.musicapp.serverapimusicapp.repository.PlaylistRepository;
import com.musicapp.serverapimusicapp.repository.SongRepository;
import com.musicapp.serverapimusicapp.service.IPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class PlaylistService implements IPlaylistService {
    @Autowired
    private PlaylistConverter playlistConverter;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private SongRepository songRepository;
    @Override
    public PlaylistDTO save(PlaylistDTO playlistDTO) {
        PlaylistEntity playlistEntity = new PlaylistEntity();
        if(playlistDTO.getId() != null){
            PlaylistEntity oldata = findByID(playlistDTO.getId());
            if(oldata != null){
                playlistEntity = playlistConverter.toEntity( playlistDTO ,oldata);
            }
        }else{
            playlistEntity = playlistConverter.toEntity(playlistDTO);
        }
        List<SongEntity> songs = songRepository.findAllById(playlistDTO.getSongIds());
        playlistEntity.setSongs(songs);
        playlistEntity = playlistRepository.save(playlistEntity);
        return playlistConverter.toDTO(playlistEntity);
    }

    @Override
    public void delete(long[] ids) {
        for (long id: ids){
            playlistRepository.deleteById(id);
        }
    }

    @Override
    public List<PlaylistDTO> findAll(Pageable pageable) {
        List<PlaylistDTO> results = new ArrayList<>();
        List<PlaylistEntity> entities = playlistRepository.findAll(pageable).getContent();
        for (PlaylistEntity item : entities){
            results.add(playlistConverter.toDTO(item));
        }
        return results;
    }

    @Override
    public List<PlaylistDTO> findAll() {
        List<PlaylistDTO> results = new ArrayList<>();
        List<PlaylistEntity> entities = playlistRepository.findAll();
        for (PlaylistEntity item : entities){
            results.add(playlistConverter.toDTO(item));
        }
        return results;
    }
    @Override
    public int totalItem() {
        return (int)playlistRepository.count();
    }

    public PlaylistEntity findByID(Long id){
        Optional<PlaylistEntity> optionalPlaylist = playlistRepository.findById(id);
        if(optionalPlaylist.isPresent()){
            PlaylistEntity playlistEntity = optionalPlaylist.get();
            return playlistEntity;
        }else {
            return null;
        }
    }
}
