package com.musicapp.serverapimusicapp.service;

import com.musicapp.serverapimusicapp.api.output.BaseResponse;
import com.musicapp.serverapimusicapp.dto.PlaylistDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPlaylistService {

    PlaylistDTO save(PlaylistDTO playlistDTO);
    void delete(long[] ids);
    List<PlaylistDTO> findAll(Pageable pageable);
    List<PlaylistDTO> findAll();
    int totalItem();
    String saveFile(MultipartFile file, String url);
    BaseResponse addSongToPlaylist(Long playlistId, Long songId);
    public String findUrlAvatarById(Long id);
}
