package com.example.mediaApp.service;

import com.example.mediaApp.model.dto.CommentDTO;
import com.example.mediaApp.model.entity.CommentEntity;
import com.example.mediaApp.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final AppUserService userService;

    public CommentEntity get(long id){
        return repository
                .findById(id)
                .orElse(null);
    }

    public CommentEntity create(CommentDTO comment) {
        CommentEntity newComment = new CommentEntity();

        newComment.setText(comment.getText());
        newComment.setUser(comment.getUser() != null
                ? userService.find(comment.getUser().getId())
                : null
        );
        newComment.setLikes(Collections.emptyList());

        repository.save(newComment);

        return newComment;
    }

    public CommentEntity find(long id) {
        return repository
                .findById(id)
                .orElse(null);
    }

    public void delete(CommentEntity comment) {
        repository.delete(comment);
    }
}
