package com.example.mediaApp.repository;

import com.example.mediaApp.model.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByUserId(long userId);
}
