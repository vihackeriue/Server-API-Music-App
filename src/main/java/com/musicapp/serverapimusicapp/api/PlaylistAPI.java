package com.musicapp.serverapimusicapp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.serverapimusicapp.api.output.BaseResponse;
import com.musicapp.serverapimusicapp.api.output.PlaylistOutput;
import com.musicapp.serverapimusicapp.dto.PlaylistDTO;
import com.musicapp.serverapimusicapp.entity.PlaylistEntity;
import com.musicapp.serverapimusicapp.service.IPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
    @GetMapping(value = "/playlist/avatar/{id}")
    public ResponseEntity<byte[]> getThumnail(@PathVariable("id") Long id){
        String url = playlistService.findUrlAvatarById(id);
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
    @PostMapping(value = "/playlist")
    public PlaylistDTO createPlaylist(@RequestParam("fileImage") MultipartFile fileImage,
                                      @RequestParam("info") String songJson){
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(songJson);
        try {
            PlaylistDTO playlistDTO = objectMapper.readValue(songJson, PlaylistDTO.class);

            String url_avatar = playlistService.saveFile(fileImage, "data/playlist/image/");

            if (!url_avatar.isEmpty()) {
                playlistDTO.setUrlAvatar(url_avatar);
            }
//            System.out.println(model.getCreatedBy());
            return playlistService.save(playlistDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
    @PutMapping(value = "/playlist/{id}")
    public PlaylistDTO updatePlaylist(@RequestBody PlaylistDTO model, @PathVariable("id") Long id){
        model.setId(id);
        return playlistService.save(model);
    }
    @PostMapping(value = "/playlist/{playlistId}/song/{songId}")
    public ResponseEntity<?> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId){
        BaseResponse baseRespons =playlistService.addSongToPlaylist(playlistId, songId);
        if(baseRespons.isSuccess()){
            return ResponseEntity.ok(baseRespons);

        }else {
            return ResponseEntity.badRequest().body(baseRespons);
        }
    }
    @DeleteMapping(value = "/playlist")
    public void deletePlaylist(@RequestBody long[] ids){
        playlistService.delete(ids);
    }



}
