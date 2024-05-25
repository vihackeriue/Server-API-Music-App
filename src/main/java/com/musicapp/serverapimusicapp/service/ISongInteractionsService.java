package com.musicapp.serverapimusicapp.service;

import com.musicapp.serverapimusicapp.dto.SongDTO;
import com.musicapp.serverapimusicapp.dto.SongInteractionsDTO;

import java.util.List;

public interface ISongInteractionsService {

    SongInteractionsDTO save(SongInteractionsDTO songInteractionsDTO);

    List<SongInteractionsDTO> findByIdSong(Long idSong);
    List<SongInteractionsDTO> findAll();
}
