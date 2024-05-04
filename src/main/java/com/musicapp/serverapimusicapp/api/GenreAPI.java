package com.musicapp.serverapimusicapp.api;

import com.musicapp.serverapimusicapp.dto.GenreDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class GenreAPI {
    @PostMapping(value = "/genre")
    public GenreDTO createGenre(@RequestBody GenreDTO model){
        return model;
    }

}
