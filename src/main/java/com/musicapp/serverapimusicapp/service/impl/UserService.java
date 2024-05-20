package com.musicapp.serverapimusicapp.service.impl;

import com.musicapp.serverapimusicapp.api.output.BaseResponse;
import com.musicapp.serverapimusicapp.api.output.LoginRespone;
import com.musicapp.serverapimusicapp.cofig.JWTConfig;
import com.musicapp.serverapimusicapp.converter.UserConverter;
import com.musicapp.serverapimusicapp.dto.LoginDTO;
import com.musicapp.serverapimusicapp.dto.UserDTO;
import com.musicapp.serverapimusicapp.entity.TokenEntity;
import com.musicapp.serverapimusicapp.entity.UserEntity;
import com.musicapp.serverapimusicapp.repository.TokenRepository;
import com.musicapp.serverapimusicapp.repository.UserRepository;
import com.musicapp.serverapimusicapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTConfig tokenConfig;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        UserEntity userEnity = new UserEntity();

        String password = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(password);
        if(userDTO.getId() != null){
//            UserEnity oldData = userRepository.findAllById(userDTO.getId());
//            userEnity = userConverter.toEntity(userDTO);
        }else {

            userEnity = userConverter.toEntity(userDTO);
            userEnity.setCreatedBy(userDTO.getEmail());
            userEnity.setCreatedBy(userDTO.getEmail());
        }


        userEnity = userRepository.save(userEnity);

        return userConverter.toDTO(userEnity);
    }

    @Override
    public LoginRespone login(LoginDTO loginDTO) {
        String msg = "";
        String token = null;
        Optional<UserEntity> userEnityOptional = userRepository.findByEmail(loginDTO.getEmail());
        if(userEnityOptional.isPresent()){
            UserEntity enity = userEnityOptional.get();
            UserDTO userDTO = userConverter.toDTO(enity);
            if(passwordEncoder.matches(loginDTO.getPassword(), enity.getPassword())){
                token = tokenConfig.generateToken(enity.getEmail());
                TokenEntity tokenEntity = new TokenEntity();
                tokenEntity.setCreatedBy(enity.getEmail());
                tokenEntity.setUpdatedBy(enity.getEmail());
                tokenEntity.setToken(token);
                tokenEntity.setStatus(true);
                tokenRepository.save(tokenEntity);
                System.out.println(token);
                System.out.println(tokenConfig.extractUsername(token));
                return new LoginRespone(true, "Đăng nhập thành công!", token, userDTO);
            }else
                return new LoginRespone(false, "Mật khẩu không đúng!", token, null);
        }else {
            return new LoginRespone(false, "Email không tồn tại!", token, null);
        }

    }


    @Override
    public String findByEmail(String email) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if(userEntityOptional.isPresent()){
            UserEntity user = userEntityOptional.get();
            return user.getEmail();
        }
        return null;
    }

    @Override
    public BaseResponse logout(String token) {

        if (token == null) {
            return new BaseResponse(false, "Đăng xuất không thành công");
        }
        Optional<TokenEntity> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isPresent()) {
            TokenEntity storedToken = optionalToken.get();
            storedToken.setStatus(false); // Cập nhật trạng thái của token
            storedToken.setUpdatedBy(tokenConfig.extractUsername(token));
            tokenRepository.save(storedToken); // Lưu trạng thái mới vào cơ sở dữ liệu
            return new BaseResponse(true, "Đăng xuất thành công");
        } else {
            return new BaseResponse(false, "Không tìm thấy token");
        }
    }

    @Override
    public boolean isToken(String token) {
        Optional<TokenEntity> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isPresent()) {
            TokenEntity storedToken = optionalToken.get();
            System.out.println(storedToken.getStatus());
            return storedToken.getStatus();
        }
        return false;
    }
    @Override
    public String saveFile(MultipartFile file, String url) {
        // Lưu file trên server
        // Tạo ID duy nhất cho bài hát
        String songId = UUID.randomUUID().toString();
        // Lưu file với tên là ID của bài hát
        Path path = Paths.get(url + songId + getFileExtension(file.getOriginalFilename()));
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public UserDTO updateFrofile(UserDTO userDTO) {
        if(userDTO.getId() != null){
            UserEntity user = userConverter.toEntity(userDTO);

            return userConverter.toDTO(userRepository.save(user));
        }
        return null;
    }
    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }
}
