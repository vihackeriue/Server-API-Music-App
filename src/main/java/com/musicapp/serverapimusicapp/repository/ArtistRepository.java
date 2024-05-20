package com.musicapp.serverapimusicapp.repository;

import com.musicapp.serverapimusicapp.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {
    @Query("SELECT a FROM ArtistEntity a WHERE a.user.id = :idUser")
    List<ArtistEntity> findByIDUser(@Param("idUser") Long idUser);
}
