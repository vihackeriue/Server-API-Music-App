package com.musicapp.serverapimusicapp.repository;

import com.musicapp.serverapimusicapp.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {
}
