package com.musicapp.serverapimusicapp.repository;

import com.musicapp.serverapimusicapp.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {
}
