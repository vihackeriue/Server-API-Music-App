package com.musicapp.serverapimusicapp.converter;

import com.musicapp.serverapimusicapp.dto.SongDTO;
import com.musicapp.serverapimusicapp.dto.SongInteractionsDTO;
import com.musicapp.serverapimusicapp.entity.SongEntity;
import com.musicapp.serverapimusicapp.entity.SongInteractionsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SongConverter extends BaseConverter<SongDTO, SongEntity>{

    @Autowired
    private GenreConverter genreConverter;

    @Autowired
    private ArtistConverter artistConverter;
    @Override
    public SongEntity toEntity(SongDTO songDTO) {
        SongEntity songEntity = new SongEntity();
        if(songDTO.getId()!= null){
            songEntity.setId(songDTO.getId());
        }

        songEntity.setTitle(songDTO.getTitle());
        songEntity.setDescription(songDTO.getDescription());
        songEntity.setUrl_audio(songDTO.getUrl_audio());
        songEntity.setUrl_thumbnail(songDTO.getUrl_thumbnail());
        songEntity.setViews(songDTO.getViews());
        convertToEntityBase(songDTO, songEntity);


        return  songEntity;
    }
    @Override
    public SongDTO toDTO(SongEntity songEntity) {
        SongDTO songDTO = new SongDTO();
        if(songEntity.getId() != null){
            songDTO.setId(songEntity.getId());
        }
        songDTO.setTitle(songEntity.getTitle());
        songDTO.setDescription(songEntity.getDescription());
        songDTO.setUrl_audio(songEntity.getUrl_audio());
        songDTO.setUrl_thumbnail(songEntity.getUrl_thumbnail());
        songDTO.setViews(songEntity.getViews());
        songDTO.setViews(songEntity.getViews());
        if(songEntity.getArtist() != null){
//            songDTO.setArtistID(songEntity.getArtist().getId());
            songDTO.setArtistDTO(artistConverter.toDTO(songEntity.getArtist()));
        }
        if(songEntity.getGenre() != null){
//            songDTO.setGenreID(songEntity.getGenre().getId());
            songDTO.setGenreDTO(genreConverter.toDTO(songEntity.getGenre()));
        }

        List<Long> interactionsIDDTO = new ArrayList<>();
        if(songEntity.getSongInteractions() != null){

            for (SongInteractionsEntity item : songEntity.getSongInteractions()){
                interactionsIDDTO.add(item.getId());
            }
            songDTO.setSongInteractionsID(interactionsIDDTO);
        }
        convertToDTOBase(songDTO,songEntity);
        return  songDTO;
    }
    @Override
    public SongEntity toEntity(SongDTO dto, SongEntity entity){
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setUrl_audio(dto.getUrl_audio());
        entity.setUrl_thumbnail(dto.getUrl_thumbnail());
        entity.setViews(dto.getViews());
        convertToEntityBase(dto, entity);

        return entity;
    }

}
