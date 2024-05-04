package com.musicapp.serverapimusicapp.repository;

import com.musicapp.serverapimusicapp.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    GenreEntity findOneByCode(String code);
}
