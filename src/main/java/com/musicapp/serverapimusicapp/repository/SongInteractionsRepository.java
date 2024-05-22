package com.musicapp.serverapimusicapp.repository;

import com.musicapp.serverapimusicapp.entity.ArtistEntity;
import com.musicapp.serverapimusicapp.entity.SongInteractionsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SongInteractionsRepository extends JpaRepository<SongInteractionsEntity, Long> {
    @Query("SELECT a FROM SongInteractionsEntity a WHERE a.song.id = :idSong")
    List<SongInteractionsEntity> findByIDSong(@Param("idSong") Long idSong);
    @Modifying
    @Transactional
    @Query("UPDATE SongInteractionsEntity s SET s.views = s.views + 1 WHERE s.song.id = :songId AND s.user.id = :userId")
    int incrementViews(@Param("songId") Long songId, @Param("userId") Long userId);

    @Query("SELECT s FROM SongInteractionsEntity s WHERE s.song.id = :songId AND s.user.id = :userId")
    Optional<SongInteractionsEntity> findBySongIdAndUserId(@Param("songId") Long songId, @Param("userId") Long userId);
    @Modifying
    @Transactional
    @Query("UPDATE SongInteractionsEntity s SET s.rating = :rating WHERE s.song.id = :songId AND s.user.id = :userId")
    int updateRating(@Param("songId") Long songId, @Param("userId") Long userId, @Param("rating") int rating);
}
