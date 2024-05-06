package com.musicapp.serverapimusicapp.repository;

import com.musicapp.serverapimusicapp.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SongRepository extends JpaRepository<SongEntity, Long> {
    Optional<SongEntity> findById(Long id);
}
