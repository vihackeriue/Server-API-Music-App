package com.musicapp.serverapimusicapp.api;

import com.musicapp.serverapimusicapp.api.output.PlaylistOutput;
import com.musicapp.serverapimusicapp.dto.PlaylistDTO;
import com.musicapp.serverapimusicapp.service.IPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
public class PlaylistAPI extends BaseAPI{

    @Autowired
    private IPlaylistService playlistService;

    @GetMapping(value = "/playlist")
    public PlaylistOutput showPlaylist(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "limit", required = false) Integer limit){
        PlaylistOutput playlistOutput = new PlaylistOutput();
        if(page != null || limit != null){
            playlistOutput.setPage(page);
            playlistOutput.setTotalPage((int) Math.ceil((double) (playlistService.totalItem())/ limit));
            Pageable pageable = PageRequest.of(page-1, limit);
            playlistOutput.setListResult(playlistService.findAll(pageable));
        }else {
            playlistOutput.setListResult(playlistService.findAll());
        }
        return playlistOutput;
    }
    @PostMapping(value = "/playlist")
    public PlaylistDTO createPlaylist(@RequestBody PlaylistDTO model){
        System.out.println(model.getCreatedBy());
        return playlistService.save(model);
    }
    @PutMapping(value = "/playlist/{id}")
    public PlaylistDTO updatePlaylist(@RequestBody PlaylistDTO model, @PathVariable("id") Long id){
        model.setId(id);
        return playlistService.save(model);
    }
    @DeleteMapping(value = "/playlist")
    public void deletePlaylist(@RequestBody long[] ids){
        playlistService.delete(ids);
    }



}
