package com.musicapp.serverapimusicapp.service;

import com.musicapp.serverapimusicapp.dto.SongDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public interface ISongService {
    SongDTO save(SongDTO songDTO);

    List<SongDTO> findAll(Pageable pageable);
    List<SongDTO> findAll();
    int totalItem();

    void delete(long[] ids);

    SongDTO findByID(long id, long idUser);
    SongDTO findByID(long idsong);

    long updateView(SongDTO songDTO);

    String saveFile(MultipartFile file, String url);
    String findUrlAudioById(Long id);
    String findUrlAvatarById(Long id);

}
