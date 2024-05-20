package com.musicapp.serverapimusicapp.converter;

import com.musicapp.serverapimusicapp.dto.UserDTO;
import com.musicapp.serverapimusicapp.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends BaseConverter<UserDTO, UserEntity>{
    @Override
    public UserDTO toDTO(UserEntity entity) {

        UserDTO userDTO = new UserDTO();
        if(entity.getId()!= null){
            userDTO.setId(entity.getId());
        }
        userDTO.setEmail(entity.getEmail());
        userDTO.setPassword(entity.getPassword());
        userDTO.setFullName(entity.getFullName());
        userDTO.setPhoneNumber(entity.getPhoneNumber());
        userDTO.setStatus(entity.getStatus());
        userDTO.setUrlAvatar(entity.getUrlAvatar());
        userDTO.setUserPreferences(entity.getUserPreferences());
        return userDTO;
    }

    @Override
    public UserEntity toEntity(UserDTO dto) {
        UserEntity userEnity = new UserEntity();
        userEnity.setEmail(dto.getEmail());
        userEnity.setPassword(dto.getPassword());
        userEnity.setFullName(dto.getFullName());
        userEnity.setPhoneNumber(dto.getPhoneNumber());
        userEnity.setStatus(dto.getStatus());
        userEnity.setUrlAvatar(dto.getUrlAvatar());
        userEnity.setUserPreferences(dto.getUserPreferences());
        return userEnity;
    }

    @Override
    public UserEntity toEntity(UserDTO dto, UserEntity entity) {
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setFullName(dto.getFullName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setStatus(dto.getStatus());
        entity.setUrlAvatar(dto.getUrlAvatar());
        entity.setUserPreferences(dto.getUserPreferences());
        return entity;
    }
}
