package com.musicapp.serverapimusicapp.repository;

import com.musicapp.serverapimusicapp.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    Optional<GenreEntity> findById(Long id);
}
