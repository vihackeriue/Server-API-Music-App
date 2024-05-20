package com.musicapp.serverapimusicapp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.serverapimusicapp.api.output.SongOutput;
import com.musicapp.serverapimusicapp.dto.SongDTO;
import com.musicapp.serverapimusicapp.service.ISongService;
import jakarta.servlet.http.HttpServletRequest;
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
    @GetMapping(value = "/song/{id}")
    public SongDTO showSongByID(@PathVariable("id") Long id){
        if(id != null){
            return songService.findByID(id);
        }
        return null;
    }
    @GetMapping(value = "/song/audio")
    public ResponseEntity<byte[]> getSongAudio(HttpServletRequest request){
        String url = request.getHeader("urlAudio");
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
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", audioFile.getName());
            System.out.println(headers);
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping(value = "/song/avatar/{id}")
    public ResponseEntity<byte[]> getAvatar(HttpServletRequest request){
        String url = request.getHeader("urlAvatar");
        File avatarFile = new File(url);
        try {
            byte[] fileContent = Files.readAllBytes(avatarFile.toPath());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // or MediaType.IMAGE_PNG based on your image type
            headers.setContentDispositionFormData("attachment", avatarFile.getName());
            System.out.println(headers);
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping(value = "/song")
    public  ResponseEntity<SongDTO>  createSong(@RequestParam("fileAudio") MultipartFile fileAudio,
                                                @RequestParam("fileImage") MultipartFile fileImage,
                                                @RequestParam("info") String songJson){
        System.out.println(songJson);

        // Chuyển đổi chuỗi JSON thành đối tượng Java
        ObjectMapper objectMapper = new ObjectMapper();
        SongDTO model = null;
        try {
            model = objectMapper.readValue(songJson, SongDTO.class);
            String url_audio = songService.saveFile(fileAudio, "music/data/audio/");
            String url_avatar = songService.saveFile(fileImage, "music/data/image/");
            SongDTO savedSong = null;
            if (!url_audio.isEmpty() && !url_avatar.isEmpty()) {
                model.setUrl_audio(url_audio);
                model.setUrl_thumbnail(url_avatar);
                savedSong = songService.save(model);
            }

            return ResponseEntity.ok(savedSong);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
