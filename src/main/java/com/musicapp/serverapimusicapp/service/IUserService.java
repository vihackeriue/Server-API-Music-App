package com.musicapp.serverapimusicapp.service;

import com.musicapp.serverapimusicapp.api.output.BaseResponse;
import com.musicapp.serverapimusicapp.dto.LoginDTO;
import com.musicapp.serverapimusicapp.dto.UserDTO;

public interface IUserService {
    boolean existsByEmail(String email);
    UserDTO register(UserDTO userDTO);

    BaseResponse login(LoginDTO loginDTO);


    String findByEmail(String email);

    BaseResponse logout(String token);

    boolean isToken(String token);

}
