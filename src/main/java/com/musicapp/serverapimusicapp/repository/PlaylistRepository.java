package com.musicapp.serverapimusicapp.repository;

import com.musicapp.serverapimusicapp.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {

    @Query("SELECT s.urlAvatar FROM PlaylistEntity s WHERE s.id = :id")
    String findUrlAvatarById(Long id);
}
