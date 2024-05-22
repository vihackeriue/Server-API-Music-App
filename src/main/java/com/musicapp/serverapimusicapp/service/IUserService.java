package com.musicapp.serverapimusicapp.service;

import com.musicapp.serverapimusicapp.api.output.BaseResponse;
import com.musicapp.serverapimusicapp.dto.LoginDTO;
import com.musicapp.serverapimusicapp.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {
    boolean existsByEmail(String email);
    UserDTO register(UserDTO userDTO);

    BaseResponse login(LoginDTO loginDTO);


    String findByEmail(String email);
    Long findIDByEmail(String token);

    BaseResponse logout(String token);

    boolean isToken(String token);

    String saveFile(MultipartFile file, String url);

    UserDTO updateFrofile(UserDTO userDTO);
    public String findUrlAvatarById(Long id);


}
