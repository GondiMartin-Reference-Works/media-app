package com.example.mediaApp.service;

import com.example.mediaApp.model.entity.CommentEntity;
import com.example.mediaApp.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;

    public CommentEntity get(long id){
        return repository
                .findById(id)
                .orElse(null);
    }
}
