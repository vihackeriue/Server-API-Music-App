package com.musicapp.serverapimusicapp.api.output;

public class LoginRespone extends BaseResponse{
    private String token;
    public LoginRespone(boolean success, String message, String token) {
        super(success, message);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
