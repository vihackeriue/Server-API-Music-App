package com.musicapp.serverapimusicapp.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.serverapimusicapp.api.output.BaseResponse;

import com.musicapp.serverapimusicapp.dto.LoginDTO;
import com.musicapp.serverapimusicapp.dto.UserDTO;
import com.musicapp.serverapimusicapp.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@CrossOrigin
@RestController
public class UserAPI extends BaseAPI{

    @Autowired
    private IUserService userService;
    @PostMapping(value = "/register")
    public ResponseEntity<?> Register(@RequestBody UserDTO userDTO){
        if(userService.existsByEmail(userDTO.getEmail())){
            return ResponseEntity.badRequest().body(new BaseResponse(false, "Email đã tồn tại!"));
        }
        userService.register(userDTO);
        return ResponseEntity.ok(new BaseResponse(true, "Đăng ký thành công!"));
    }
    @PostMapping(value = "/login")
    public ResponseEntity<?> Login(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(userService.login(loginDTO));
    }
    @PostMapping(value = "/profile/update/{id}")
    public UserDTO updateFrofile(@RequestParam("fileImage") MultipartFile fileImage,
                                           @RequestParam("info") String userJson,
                                           @PathVariable("id") Long id){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UserDTO model = objectMapper.readValue(userJson, UserDTO.class);
            model.setId(id);
            String urlAvatar = userService.saveFile(fileImage,"data/user/");
            if(!urlAvatar.isEmpty()){
                model.setUrlAvatar(urlAvatar);
            }
            return userService.updateFrofile(model);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
    @GetMapping(value = "/profile/avatar/{id}")
    public ResponseEntity<byte[]> getAvatarUser(@PathVariable("id") Long id){
        String url = userService.findUrlAvatarById(id);
        System.out.println(url);
        File audioFile = new File(url);
        byte[] fileContent = null;
        HttpHeaders headers;
        try {
            if (!audioFile.exists() || !audioFile.isFile()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            fileContent = Files.readAllBytes(audioFile.toPath());
            headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentDispositionFormData("attachment", audioFile.getName());
            System.out.println(headers);
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping(value = "/logout")
    public ResponseEntity<?> Login(HttpServletRequest request){
        final String token = request.getHeader("token");
        return ResponseEntity.ok(userService.logout(token));
    }
}
