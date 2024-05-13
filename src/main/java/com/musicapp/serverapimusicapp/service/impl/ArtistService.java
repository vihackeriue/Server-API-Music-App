package com.musicapp.serverapimusicapp.service.impl;

import com.musicapp.serverapimusicapp.converter.ArtistConverter;
import com.musicapp.serverapimusicapp.dto.ArtistDTO;
import com.musicapp.serverapimusicapp.entity.ArtistEntity;
import com.musicapp.serverapimusicapp.entity.GenreEntity;
import com.musicapp.serverapimusicapp.repository.ArtistRepository;
import com.musicapp.serverapimusicapp.service.IArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ArtistService implements IArtistService {
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ArtistConverter artistConverter;

    @Override
    public ArtistDTO save(ArtistDTO artistDTO) {
        ArtistEntity artistEntity = new ArtistEntity();
        System.out.println(artistDTO.getId());
        if(artistDTO.getId() != null){

            ArtistEntity oldDataEntity = findByID(artistDTO.getId());
            if(oldDataEntity != null){
                System.out.println(oldDataEntity.getId());
                artistEntity = artistConverter.toEntity(artistDTO, oldDataEntity);
            }
        }else {
            artistEntity = artistConverter.toEntity(artistDTO);
        }
        System.out.println(artistEntity.getId());
        artistEntity = artistRepository.save(artistEntity);
        return artistConverter.toDTO(artistEntity);
    }
    @Override
    public void delete(long[] ids) {
        for (long item: ids){
            artistRepository.deleteById(item);
        }
    }
    @Override
    public List<ArtistDTO> findAll(Pageable pageable) {
        List<ArtistDTO> results = new ArrayList<>();
        List<ArtistEntity> entities = artistRepository.findAll(pageable).getContent();
        for(ArtistEntity item: entities){
            ArtistDTO artistDTO = artistConverter.toDTO(item);
            results.add(artistDTO);
        }
        return results;
    }
    @Override
    public List<ArtistDTO> findAll() {
        List<ArtistDTO> results = new ArrayList<>();
        List<ArtistEntity> artistEntity = artistRepository.findAll();
        for(ArtistEntity item: artistEntity){
            ArtistDTO artistDTO = artistConverter.toDTO(item);
            results.add(artistDTO);
        }
        return results;
    }
    @Override
    public int totalItem() {
        return (int) artistRepository.count();
    }
//    tìm kiếm artist theo ID
    public ArtistEntity findByID(Long id){
        Optional<ArtistEntity> optionalArtist = artistRepository.findById(id);
        if (optionalArtist.isPresent()) {
            ArtistEntity DataEntity = optionalArtist.get();
            return DataEntity;
        } else {
            return null;
        }
    }
}
