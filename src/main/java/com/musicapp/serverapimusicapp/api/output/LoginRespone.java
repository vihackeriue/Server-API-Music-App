package com.musicapp.serverapimusicapp.api.output;

import com.musicapp.serverapimusicapp.dto.UserDTO;

public class LoginRespone extends BaseResponse{
    private String token;

    private UserDTO userDTO;

    public LoginRespone(boolean success, String mess, String token, UserDTO userDTO) {
        super(success,mess);
        this.token = token;
        this.userDTO = userDTO;
    }




    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
