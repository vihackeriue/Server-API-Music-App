package com.musicapp.serverapimusicapp.converter;

import com.musicapp.serverapimusicapp.dto.BaseDTO;
import com.musicapp.serverapimusicapp.dto.GenreDTO;
import com.musicapp.serverapimusicapp.entity.BaseEntity;

public abstract class BaseConverter<DTO extends BaseDTO, Entity extends BaseEntity> {
    public void convertToDTOBase(final DTO dto, final Entity entity ){
        dto.setCreateDate(entity.getCreateDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setUpdatedBy(entity.getUpdatedBy());
    }
    public abstract DTO toDTO(Entity entity);
    public abstract Entity toEntity(DTO dto);

    public abstract Entity toEntity(DTO dto, Entity entity);

}
