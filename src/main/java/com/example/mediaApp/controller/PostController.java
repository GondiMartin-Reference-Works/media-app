package com.example.mediaApp.controller;

import com.example.mediaApp.model.dto.AppUserDTO;
import com.example.mediaApp.model.dto.CommentDTO;
import com.example.mediaApp.model.dto.GroupDTO;
import com.example.mediaApp.model.dto.LikeDTO;
import com.example.mediaApp.model.dto.PostDTO;
import com.example.mediaApp.model.entity.PostEntity;
import com.example.mediaApp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${base.backend.url}/post")
@RequiredArgsConstructor
@CrossOrigin(origins = "${base.frontend.url}")
public class PostController {

    private final PostService service;

    @GetMapping()
    public ResponseEntity<List<PostDTO>> getAll(){
        return ResponseEntity.ok(service.getAll()
                .stream().map(post -> new PostDTO(
                        post.getUser() != null
                                ? new AppUserDTO(
                                post.getUser().getId(),
                                post.getUser().getFirstName(),
                                post.getUser().getLastName())
                                : null,
                        post.getGroup() != null
                                ? new GroupDTO(
                                post.getGroup().getId(),
                                post.getGroup().getName())
                                : null,
                        post.getText(),
                        post.getImage(),
                        post.getLikes()
                                .stream()
                                .map(like -> new LikeDTO(
                                        new AppUserDTO(
                                                like.getUser().getId(),
                                                like.getUser().getFirstName(),
                                                like.getUser().getLastName()
                                        )
                                ))
                                .toList(),
                        post.getComments()
                                .stream()
                                .map(comment -> new CommentDTO(
                                        comment.getText(),
                                        new AppUserDTO(
                                                comment.getUser().getId(),
                                                comment.getUser().getFirstName(),
                                                comment.getUser().getLastName()
                                        )
                                ))
                                .toList()
                ))
                .toList()
        );
    }

    @PostMapping()
    public ResponseEntity<PostEntity> create(@RequestBody PostDTO post){
        return ResponseEntity.ok(service.create(post));
    }
}
