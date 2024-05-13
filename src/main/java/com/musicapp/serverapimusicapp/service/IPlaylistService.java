package com.musicapp.serverapimusicapp.service;

import com.musicapp.serverapimusicapp.dto.PlaylistDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPlaylistService {

    PlaylistDTO save(PlaylistDTO playlistDTO);
    void delete(long[] ids);
    List<PlaylistDTO> findAll(Pageable pageable);
    List<PlaylistDTO> findAll();
    int totalItem();
}
