package com.musicapp.serverapimusicapp.repository;

import com.musicapp.serverapimusicapp.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SongRepository extends JpaRepository<SongEntity, Long> {
    Optional<SongEntity> findById(Long id);
    @Query("SELECT s.url_audio FROM SongEntity s WHERE s.id = :id")
    String findUrlAudioById(Long id);
    @Query("SELECT s.url_thumbnail FROM SongEntity s WHERE s.id = :id")
    String findUrlAvatarById(Long id);
}
