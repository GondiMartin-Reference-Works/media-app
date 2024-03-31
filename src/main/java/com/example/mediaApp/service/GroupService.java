package com.example.mediaApp.service;

import com.example.mediaApp.model.entity.GroupEntity;
import com.example.mediaApp.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository repository;

    public GroupEntity find(long id){
        return repository
                .findById(id)
                .orElse(null);
    }
}
