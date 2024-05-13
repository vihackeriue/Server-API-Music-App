package com.musicapp.serverapimusicapp.converter;

import com.musicapp.serverapimusicapp.dto.ArtistDTO;
import com.musicapp.serverapimusicapp.dto.GenreDTO;
import com.musicapp.serverapimusicapp.entity.ArtistEntity;
import com.musicapp.serverapimusicapp.entity.GenreEntity;
import com.musicapp.serverapimusicapp.entity.SongEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArtistConverter extends BaseConverter<ArtistDTO, ArtistEntity>{


    public ArtistEntity toEntity(ArtistDTO dto){
        ArtistEntity entity = new ArtistEntity();
        entity.setName(dto.getName());
        entity.setUrlAvatar(dto.getUrl_avatar());
        convertToEntityBase(dto,entity);
        return entity;
    }
    public ArtistDTO toDTO(ArtistEntity entity){
        ArtistDTO dto = new ArtistDTO();
        if(entity.getId() != null){
            dto.setId(entity.getId());
        }
        dto.setName(entity.getName());
        dto.setUrl_avatar(entity.getUrlAvatar());

        List<Long> songIds = new ArrayList<>();
        for (SongEntity item : entity.getSongs()){
            songIds.add(item.getId());
        }
        dto.setSongIds(songIds);

        convertToDTOBase(dto,entity);
//        dto.setCreateDate(entity.getCreateDate());
//        dto.setCreatedBy(entity.getCreatedBy());
//        dto.setUpdatedDate(entity.getUpdatedDate());
//        dto.setUpdatedBy(entity.getUpdatedBy());
        return dto;
    }

    public ArtistEntity toEntity(ArtistDTO dto, ArtistEntity  entity){
        entity.setName(dto.getName());
        entity.setUrlAvatar(dto.getUrl_avatar());
        convertToEntityBase(dto,entity);

        return entity;
    }
}
