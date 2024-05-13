package com.musicapp.serverapimusicapp.converter;

import com.musicapp.serverapimusicapp.dto.GenreDTO;
import com.musicapp.serverapimusicapp.entity.GenreEntity;
import com.musicapp.serverapimusicapp.entity.SongEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenreConverter extends BaseConverter<GenreDTO, GenreEntity> {
    @Override
    public GenreEntity toEntity(GenreDTO dto){
        GenreEntity entity = new GenreEntity();
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());

        convertToEntityBase(dto,entity);
        return entity;
    }
    @Override
    public GenreDTO toDTO(GenreEntity entity){
        GenreDTO dto = new GenreDTO();
        if(entity.getId() != null){
            dto.setId(entity.getId());
        }
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());

        List<Long> songIds = new ArrayList<>();
        for (SongEntity item : entity.getSongs()){
            songIds.add(item.getId());
        }
        dto.setSongIds(songIds);
//        dto.setCreateDate(entity.getCreateDate());
//        dto.setCreatedBy(entity.getCreatedBy());
//        dto.setUpdatedDate(entity.getUpdatedDate());
//        dto.setUpdatedBy(entity.getUpdatedBy());
        convertToDTOBase(dto,entity);
        return dto;
    }
    @Override
    public GenreEntity toEntity(GenreDTO dto, GenreEntity  entity){
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        convertToEntityBase(dto,entity);
        return entity;
    }
}
