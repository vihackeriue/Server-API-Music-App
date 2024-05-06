package com.musicapp.serverapimusicapp.converter;

import com.musicapp.serverapimusicapp.dto.SongDTO;
import com.musicapp.serverapimusicapp.entity.SongEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SongConverter extends BaseConverter<SongDTO, SongEntity>{

    @Autowired
    private GenreConverter genreConverter;

    @Autowired
    private ArtistConverter artistConverter;
    @Override
    public SongEntity toEntity(SongDTO songDTO) {
        SongEntity songEntity = new SongEntity();
        songEntity.setTitle(songDTO.getTitle());
        songEntity.setDescription(songDTO.getDescription());
        songEntity.setUrl_audio(songDTO.getUrl_audio());
        songEntity.setUrl_thumbnail(songDTO.getUrl_thumbnail());

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
        if(songEntity.getArtist() != null){
            songDTO.setArtistDTO(artistConverter.toDTO(songEntity.getArtist()));
        }
        if(songEntity.getGenre() != null){
            songDTO.setGenreDTO(genreConverter.toDTO(songEntity.getGenre()));
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
        return entity;
    }

}
