package com.musicapp.serverapimusicapp.api;

import com.musicapp.serverapimusicapp.api.output.SongOutput;
import com.musicapp.serverapimusicapp.dto.SongDTO;
import com.musicapp.serverapimusicapp.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class SongAPI extends BaseAPI{
    @Autowired
    private ISongService songService;
    @GetMapping(value = "/song")
    public SongOutput showSong(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "limit" , required = false) Integer limit){
         SongOutput songOutput = new SongOutput();
         if(page != null && limit != null){
             songOutput.setPage(page);
             Pageable pageable = PageRequest.of(page-1, limit);
             songOutput.setListResult(songService.findAll(pageable));
             songOutput.setTotalPage((int) Math.ceil((double) (songService.totalItem())/ limit));
         }else {
             songOutput.setListResult(songService.findAll());
         }
         return songOutput;
    }
    @PostMapping(value = "/song")
    public SongDTO createSong(@RequestBody SongDTO model){
        return songService.save(model);
    }
    @PutMapping(value = "/song/{id}")
    public SongDTO updateSong(@RequestBody SongDTO songDTO, @PathVariable("id") Long id){
        songDTO.setId(id);
        return songService.save(songDTO);
    }
    @DeleteMapping(value = "/song")
    public void deleteGenre(@RequestBody long[] ids){
        songService.delete(ids);
    }

}
