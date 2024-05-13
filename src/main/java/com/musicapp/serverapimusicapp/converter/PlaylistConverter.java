package com.musicapp.serverapimusicapp.converter;

import com.musicapp.serverapimusicapp.dto.PlaylistDTO;
import com.musicapp.serverapimusicapp.entity.PlaylistEntity;
import com.musicapp.serverapimusicapp.entity.SongEntity;
import com.musicapp.serverapimusicapp.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class PlaylistConverter extends BaseConverter<PlaylistDTO, PlaylistEntity>{
    @Override
    public PlaylistDTO toDTO(PlaylistEntity entity) {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        if(entity.getId() != null){
            playlistDTO.setId(entity.getId());
        }
        playlistDTO.setName(entity.getName());
        playlistDTO.setDescription(entity.getDescription());
        playlistDTO.setUrlAvatar(entity.getUrlAvatar());
        List<Long> songIds = new ArrayList<>();
        for (SongEntity item : entity.getSongs()){
            songIds.add(item.getId());
        }
        playlistDTO.setSongIds(songIds);
        convertToDTOBase(playlistDTO, entity);
        return playlistDTO;
    }
//    @Autowired
//    SongRepository songRepository;
    @Override
    public PlaylistEntity toEntity(PlaylistDTO dto) {
        PlaylistEntity playlistEntity = new PlaylistEntity();
        playlistEntity.setName(dto.getName());
        playlistEntity.setDescription(dto.getDescription());
        playlistEntity.setUrlAvatar(dto.getUrlAvatar());
        convertToEntityBase(dto, playlistEntity);
//        List<SongEntity> songs = songRepository.findAllById(dto.getSongIds());
//        playlistEntity.setSongs(songs);
        return playlistEntity;
    }
    @Override
    public PlaylistEntity toEntity(PlaylistDTO dto, PlaylistEntity entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setUrlAvatar(dto.getUrlAvatar());
        convertToEntityBase(dto, entity);
        return entity;
    }
}
