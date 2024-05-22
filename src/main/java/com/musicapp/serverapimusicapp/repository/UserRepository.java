package com.musicapp.serverapimusicapp.repository;

import com.musicapp.serverapimusicapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT s.urlAvatar FROM UserEntity s WHERE s.id = :id")
    String findUrlAvatarById(Long id);
}
