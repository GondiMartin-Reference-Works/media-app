package com.example.mediaApp.service;

import com.example.mediaApp.model.dto.LikeDTO;
import com.example.mediaApp.model.entity.LikeEntity;
import com.example.mediaApp.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository repository;
    private final AppUserService userService;

    public LikeEntity get(long id){
        return repository
                .findById(id)
                .orElse(null);
    }

    public LikeEntity create(LikeDTO like){
        LikeEntity newLike = new LikeEntity();

        newLike.setUser(like.getUser() != null
                ? userService.find(like.getUser().getId())
                : null
        );

        repository.save(newLike);

        return newLike;
    }

    public void delete(LikeEntity like){
        repository.delete(like);
    }
}
