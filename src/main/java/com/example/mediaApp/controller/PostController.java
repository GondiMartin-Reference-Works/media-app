package com.example.mediaApp.controller;

import com.example.mediaApp.converter.PostConverter;
import com.example.mediaApp.model.dto.PostDTO;
import com.example.mediaApp.model.entity.PostEntity;
import com.example.mediaApp.model.enums.PostLike;
import com.example.mediaApp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${base.backend.url}")
@RequiredArgsConstructor
@CrossOrigin(origins = "${base.frontend.url}")
public class PostController{

    private final PostService service;
    private final PostConverter postConverter;

    @GetMapping(value = "/post")
    public ResponseEntity<List<PostDTO>> getAll(){
        return ResponseEntity.ok(service.getAll()
                .stream().map(postConverter::convertFromEntityToDTO)
                .toList()
                .reversed()
        );
    }

    @PostMapping("/post")
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO post){
        PostEntity newPostEntity = service.create(post);
        PostDTO newPostDTO = postConverter.convertFromEntityToDTO(newPostEntity);
        return ResponseEntity.ok(newPostDTO);
    }

    @PostMapping(value = "/post/{postId}/{method}")
    public ResponseEntity<PostDTO> likePostById(@PathVariable long postId,
                                                @PathVariable PostLike method,
                                                @RequestBody long userId){
        if (method.equals(PostLike.NUll))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<PostEntity> postEntity = service.likeOrUnlikePostById(postId, userId, method);

        return postEntity.map(entity ->
                ResponseEntity.ok(postConverter.convertFromEntityToDTO(entity)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
