package com.musicapp.serverapimusicapp.service;

import com.musicapp.serverapimusicapp.dto.SongDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISongService {
    SongDTO save(SongDTO songDTO);

    List<SongDTO> findAll(Pageable pageable);
    List<SongDTO> findAll();
    int totalItem();

    void delete(long[] ids);

    SongDTO findByID(long id);
}
