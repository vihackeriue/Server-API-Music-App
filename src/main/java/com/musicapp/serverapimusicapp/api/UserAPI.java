package com.musicapp.serverapimusicapp.api;


import com.musicapp.serverapimusicapp.api.output.BaseResponse;

import com.musicapp.serverapimusicapp.dto.LoginDTO;
import com.musicapp.serverapimusicapp.dto.UserDTO;
import com.musicapp.serverapimusicapp.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(value = "/logout")
    public ResponseEntity<?> Login(HttpServletRequest request){
        final String token = request.getHeader("token");
        return ResponseEntity.ok(userService.logout(token));
    }
}
