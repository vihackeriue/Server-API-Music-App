package com.musicapp.serverapimusicapp.converter;

import com.musicapp.serverapimusicapp.dto.GenreDTO;
import com.musicapp.serverapimusicapp.entity.GenreEntity;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter {
    public GenreEntity toEntity(GenreDTO dto){
        GenreEntity entity = new GenreEntity();
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        return entity;
    }
    public GenreDTO toDTO(GenreEntity entity){
        GenreDTO dto = new GenreDTO();
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        return dto;
    }
}
