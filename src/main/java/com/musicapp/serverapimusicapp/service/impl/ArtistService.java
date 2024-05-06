package com.musicapp.serverapimusicapp.service.impl;

import com.musicapp.serverapimusicapp.dto.ArtistDTO;
import com.musicapp.serverapimusicapp.service.IArtistService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ArtistService implements IArtistService {
    @Override
    public ArtistDTO save(ArtistDTO artistDTO) {
        return null;
    }

    @Override
    public void delete(long[] ids) {

    }

    @Override
    public List<ArtistDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<ArtistDTO> findAll() {
        return null;
    }

    @Override
    public int totalItem() {
        return 0;
    }
}
