package com.musicapp.serverapimusicapp.api;

import com.musicapp.serverapimusicapp.cofig.JWTConfig;
import com.musicapp.serverapimusicapp.dto.BaseDTO;
import com.musicapp.serverapimusicapp.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;




@RequestMapping("/api")
public abstract class BaseAPI<DTO extends BaseDTO> {
    @Autowired
    private IUserService userService;
    @Autowired
    private JWTConfig jwtConfig;


    public String validateTokenAndGetUsername(String token){
        if(!jwtConfig.isTokenExpired(token)){
            String email = jwtConfig.extractUsername(token);
            System.out.println(email);
            return userService.findByEmail(email);
        }
        return null;

    }
    public boolean checkToken(DTO dto, String token){
        System.out.println(token);
        if(token != null){
            String email = validateTokenAndGetUsername(token);
            if (email != null) {
                if(dto.getId() != null){
                    dto.setUpdatedBy(email);
                }else {
                    dto.setCreatedBy(email);
                    dto.setUpdatedBy(email);
                }
                return true;
            }
            return false;
        }
        return false;
    }

}
