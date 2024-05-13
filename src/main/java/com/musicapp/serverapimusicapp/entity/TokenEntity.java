package com.musicapp.serverapimusicapp.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.w3c.dom.Text;
@Entity
@Table(name = "token")
public class TokenEntity extends BaseEntity {
    @Column
    private String token;
    @Column
    private Boolean status;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
