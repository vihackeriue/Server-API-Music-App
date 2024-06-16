package com.musicapp.serverapimusicapp.api;

import com.musicapp.serverapimusicapp.converter.SongInteractionsConverter;
import com.musicapp.serverapimusicapp.dto.SongInteractionsDTO;
import com.musicapp.serverapimusicapp.service.ISongInteractionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class SongInteractionsAPI extends BaseAPI{

    @Autowired
    private ISongInteractionsService songInteractionsService;
    @Autowired
    private SongInteractionsConverter songInteractionsConverter;

    @PostMapping(value = "/song/rate")
    private SongInteractionsDTO createRate(@RequestBody SongInteractionsDTO songInteractionsDTO){

        return songInteractionsService.save(songInteractionsDTO);
    }

    @GetMapping(value = "/song/rate/{id}")
    private List<SongInteractionsDTO> getRate(@PathVariable("id") Long idSong){
        return songInteractionsService.findByIdSong(idSong);
    }
    @GetMapping(value = "/song/interactions")
    private List<SongInteractionsDTO> getInteractions(){
        return songInteractionsService.findAll();
    }

}
