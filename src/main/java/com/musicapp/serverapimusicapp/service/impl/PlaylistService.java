package com.musicapp.serverapimusicapp.service.impl;

import com.musicapp.serverapimusicapp.api.output.BaseResponse;
import com.musicapp.serverapimusicapp.converter.PlaylistConverter;
import com.musicapp.serverapimusicapp.dto.PlaylistDTO;
import com.musicapp.serverapimusicapp.entity.ArtistEntity;
import com.musicapp.serverapimusicapp.entity.PlaylistEntity;
import com.musicapp.serverapimusicapp.entity.SongEntity;
import com.musicapp.serverapimusicapp.repository.PlaylistRepository;
import com.musicapp.serverapimusicapp.repository.SongRepository;
import com.musicapp.serverapimusicapp.service.IPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlaylistService implements IPlaylistService {
    @Autowired
    private PlaylistConverter playlistConverter;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private SongRepository songRepository;
    @Override
    public PlaylistDTO save(PlaylistDTO playlistDTO) {
        PlaylistEntity playlistEntity = new PlaylistEntity();
        if(playlistDTO.getId() != null){
            PlaylistEntity oldata = findByID(playlistDTO.getId());
            if(oldata != null){
                playlistEntity = playlistConverter.toEntity( playlistDTO ,oldata);
            }
        }else{
            playlistEntity = playlistConverter.toEntity(playlistDTO);
        }
        List<SongEntity> songs = songRepository.findAllById(playlistDTO.getSongIds());
        playlistEntity.setSongs(songs);
        playlistEntity = playlistRepository.save(playlistEntity);
        return playlistConverter.toDTO(playlistEntity);
    }

    @Override
    public void delete(long[] ids) {
        for (long id: ids){
            playlistRepository.deleteById(id);
        }
    }

    @Override
    public List<PlaylistDTO> findAll(Pageable pageable) {
        List<PlaylistDTO> results = new ArrayList<>();
        List<PlaylistEntity> entities = playlistRepository.findAll(pageable).getContent();
        for (PlaylistEntity item : entities){
            PlaylistDTO playlistDTO =playlistConverter.toDTO(item);
//            playlistDTO.setUrlAvatar("http://192.168.1.2:8081/api/playlist/avatar/"+ item.getId());
            playlistDTO.setUrlAvatar("playlist/avatar/"+ item.getId());
            results.add(playlistDTO);
        }
        return results;
    }

    @Override
    public List<PlaylistDTO> findAll() {
        List<PlaylistDTO> results = new ArrayList<>();
        List<PlaylistEntity> entities = playlistRepository.findAll();
        for (PlaylistEntity item : entities){
            PlaylistDTO playlistDTO =playlistConverter.toDTO(item);
//            playlistDTO.setUrlAvatar("http://192.168.1.2:8081/api/playlist/avatar/"+ item.getId());
                playlistDTO.setUrlAvatar("playlist/avatar/"+ item.getId());
            results.add(playlistDTO);
        }
        return results;
    }
    @Override
    public int totalItem() {
        return (int)playlistRepository.count();
    }

    public PlaylistEntity findByID(Long id){
        Optional<PlaylistEntity> optionalPlaylist = playlistRepository.findById(id);
        if(optionalPlaylist.isPresent()){
            PlaylistEntity playlistEntity = optionalPlaylist.get();
            return playlistEntity;
        }else {
            return null;
        }
    }
    @Override
    public BaseResponse addSongToPlaylist(Long playlistId, Long songId) {
        PlaylistEntity playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        SongEntity song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        if (playlist.getSongs().contains(song)) {
            return new BaseResponse(false,"Bài hát đã tồn tại" );
        }
        playlist.getSongs().add(song);
        playlistRepository.save(playlist);
        return new BaseResponse(true,"Thêm bài hát thành công!" );
    }
    @Override
    public String findUrlAvatarById(Long id) {
        return songRepository.findUrlAvatarById(id);
    }
    @Override
    public String saveFile(MultipartFile file, String url) {
        String songId = UUID.randomUUID().toString();
        // Lưu file với tên là ID của bài hát
        Path path = Paths.get(url + songId + getFileExtension(file.getOriginalFilename()));
        // Lưu file trên server
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            return path.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }
}
