package com.musicapp.serverapimusicapp.converter;

import com.musicapp.serverapimusicapp.dto.SongInteractionsDTO;
import com.musicapp.serverapimusicapp.entity.SongInteractionsEntity;
import org.springframework.stereotype.Component;

@Component
public class SongInteractionsConverter extends BaseConverter<SongInteractionsDTO, SongInteractionsEntity>{
    @Override
    public SongInteractionsDTO toDTO(SongInteractionsEntity entity) {
        SongInteractionsDTO songInteractionsDTO = new SongInteractionsDTO();
        if(entity.getId() != null){
            songInteractionsDTO.setId(entity.getId());
        }
        songInteractionsDTO.setRating(entity.getRating());
        songInteractionsDTO.setUserID(entity.getUser().getId());
        songInteractionsDTO.setSongID(entity.getSong().getId());
        songInteractionsDTO.setViews(entity.getViews());
        convertToDTOBase(songInteractionsDTO, entity);
        return songInteractionsDTO;
    }

    @Override
    public SongInteractionsEntity toEntity(SongInteractionsDTO dto) {
        SongInteractionsEntity songInteractionsEntity = new SongInteractionsEntity();

        songInteractionsEntity.setRating(dto.getRating());
        songInteractionsEntity.setViews(dto.getViews());
        convertToEntityBase(dto,songInteractionsEntity);
        return songInteractionsEntity;
    }

    @Override
    public SongInteractionsEntity toEntity(SongInteractionsDTO dto, SongInteractionsEntity entity) {


        entity.setRating(dto.getRating());
        convertToEntityBase(dto,entity);

        return entity;
    }
}
