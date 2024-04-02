package com.example.mediaApp.service;

import com.example.mediaApp.model.entity.LikeEntity;
import com.example.mediaApp.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository repository;

    public LikeEntity get(long id){
        return repository
                .findById(id)
                .orElse(null);
    }
}
