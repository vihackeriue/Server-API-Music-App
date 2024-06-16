package com.musicapp.serverapimusicapp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicapp.serverapimusicapp.api.output.RecommendSongOutput;
import com.musicapp.serverapimusicapp.api.output.SongOutput;
import com.musicapp.serverapimusicapp.dto.SongDTO;
import com.musicapp.serverapimusicapp.service.ISongService;
import com.musicapp.serverapimusicapp.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class SongAPI extends BaseAPI{
    @Autowired
    private ISongService songService;
    @Autowired
    private IUserService userService;
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
    public SongDTO showSongByID(@PathVariable("id") Long id, HttpServletRequest request){
        String bearerToken = request.getHeader("token");
        Long iduser = userService.findIDByEmail(bearerToken);
        if(id != null){
            return songService.findByID(id, iduser);
        }
        return null;
    }
//    @GetMapping(value = "/song/audio")
//    public ResponseEntity<byte[]> getSongAudio(HttpServletRequest request){
//        String url = request.getHeader("urlAudio");
//        System.out.println(url);
//        File audioFile = new File(url);
//        byte[] fileContent = null;
//        HttpHeaders headers;
//        try {
//            if (!audioFile.exists() || !audioFile.isFile()) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            fileContent = Files.readAllBytes(audioFile.toPath());
//            headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDispositionFormData("attachment", audioFile.getName());
//            System.out.println(headers);
//            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
    @GetMapping(value = "/song/audio/{id}")
    public ResponseEntity<byte[]> getSongAudio(@PathVariable("id") Long id,@RequestHeader HttpHeaders headers) {
        String url = songService.findUrlAudioById(id);
//        url = "data/music/audio/abc.mp3";
        System.out.println(url);
        File audioFile = new File(url);

        if (!audioFile.exists() || !audioFile.isFile()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        long fileLength = audioFile.length();
        List<HttpRange> ranges = headers.getRange();
        if (ranges.isEmpty()) {
            return createFullContentResponse(audioFile, fileLength);
        } else {
            HttpRange range = ranges.get(0);
            long start = range.getRangeStart(fileLength);
            long end = range.getRangeEnd(fileLength);
            return createPartialContentResponse(audioFile, start, end, fileLength);
        }
    }

    private ResponseEntity<byte[]> createFullContentResponse(File audioFile, long fileLength) {
        try {
            byte[] fileContent = Files.readAllBytes(audioFile.toPath());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", audioFile.getName());
            headers.setContentLength(fileLength);
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping(value = "/song/avatar/{id}")
    public ResponseEntity<byte[]> getThumnailAudio(@PathVariable("id") Long id){
        String url = songService.findUrlAvatarById(id);
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
    private ResponseEntity<byte[]> createPartialContentResponse(File audioFile, long start, long end, long fileLength) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(audioFile, "r")) {
            long contentLength = end - start + 1;
            byte[] content = new byte[(int) contentLength];
            randomAccessFile.seek(start);
            randomAccessFile.read(content, 0, (int) contentLength);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentLength(contentLength);
            headers.add(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileLength);
            return new ResponseEntity<>(content, headers, HttpStatus.PARTIAL_CONTENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping(value = "/song/avatar/{id}")
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
            String url_audio = songService.saveFile(fileAudio, "data/music/audio/");
            String url_avatar = songService.saveFile(fileImage, "data/music/image/");
            SongDTO savedSong = null;
            if (!url_audio.isEmpty() && !url_avatar.isEmpty()) {
                model.setUrl_audio(url_audio);
                model.setUrl_thumbnail(url_avatar);
                savedSong = songService.save(model);
            }
            System.out.println(url_avatar);

            return ResponseEntity.ok(savedSong);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping(value = "/song/tool")
    public  ResponseEntity<SongDTO>  createSong(@RequestBody SongDTO songDTO){
        SongDTO savedSong = null;
        savedSong = songService.save(songDTO);
        return ResponseEntity.ok(savedSong);
    }
    @PutMapping(value = "/song/{id}")
    public SongDTO updateSong(@RequestBody SongDTO songDTO, @PathVariable("id") Long id){
        songDTO.setId(id);
        return songService.save(songDTO);
    }
    @DeleteMapping(value = "/song")
    public void deleteSong(@RequestBody long[] ids){
        songService.delete(ids);
    }


    @GetMapping("/song/recommend")
    public  RecommendSongOutput recommend(HttpServletRequest request) {
        final String token = request.getHeader("token");
        Long userId = userService.findIDByEmail(token);
        if(userId != -1){
            RecommendSongOutput recommendSongOutput = new RecommendSongOutput();
            RestTemplate restTemplate = new RestTemplate();
            String flaskApiUrl = "http://localhost:5000/recommend/" + userId ;
            ResponseEntity<String> response = restTemplate.getForEntity(flaskApiUrl, String.class);


            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                JsonNode recommendedSongsViewNode = jsonNode.get("recommended_songs_view");
                if (recommendedSongsViewNode != null) {
                    List<SongDTO> songs = new ArrayList<>();
//                long[] idSongRate = new long[recommendedSongsViewNode.size()];

                    // Duyệt qua các phần tử của recommendedSongsViewNode và chuyển đổi chúng thành kiểu dữ liệu long
                    for (int i = 0; i < recommendedSongsViewNode.size(); i++) {
//                    idSongRate[i] = recommendedSongsViewNode.get(i).asLong();
                        songs.add(songService.findByID(recommendedSongsViewNode.get(i).asLong()));
                    }
                    recommendSongOutput.setRecommendedSongsView(songs);
                }

                JsonNode recommendedSongsRateNode = jsonNode.get("recommended_songs_rate");
                if (recommendedSongsRateNode != null) {
                    List<SongDTO> songs = new ArrayList<>();
//                long[] idSongRate = new long[recommendedSongsRateNode.size()];

                    // Duyệt qua các phần tử của recommendedSongsViewNode và chuyển đổi chúng thành kiểu dữ liệu long
                    for (int i = 0; i < recommendedSongsRateNode.size(); i++) {
//                    idSongRate[i] = recommendedSongsViewNode.get(i).asLong();
                        songs.add(songService.findByID(recommendedSongsViewNode.get(i).asLong()));
                    }
                    recommendSongOutput.setRecommendedSongsRate(songs);
                }
                System.out.println(recommendedSongsViewNode);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return recommendSongOutput;
        }
        return null;
    }
    @GetMapping("/song/list")
    public List<SongDTO> recommend(@RequestBody long[] ids){
        List<SongDTO> songs = new ArrayList<>();
        for(long id: ids){
            songs.add(songService.findByID(id));
        }
        return songs;
    }

}

