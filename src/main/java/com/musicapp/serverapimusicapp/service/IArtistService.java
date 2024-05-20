package com.musicapp.serverapimusicapp.service;

import com.musicapp.serverapimusicapp.dto.ArtistDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IArtistService {
    ArtistDTO save(ArtistDTO artistDTO);
    void delete(long[] ids);
    List<ArtistDTO> findAll(Pageable pageable);
    List<ArtistDTO> findAll();
    int totalItem();
    List<ArtistDTO> findByIDUser(Long idUser);
}
