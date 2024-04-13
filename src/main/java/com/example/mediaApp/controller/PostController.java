package com.example.mediaApp.controller;

import com.example.mediaApp.converter.PostConverter;
import com.example.mediaApp.model.dto.CommentDTO;
import com.example.mediaApp.model.dto.PostDTO;
import com.example.mediaApp.model.entity.PostEntity;
import com.example.mediaApp.model.enums.Liking;
import com.example.mediaApp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @DeleteMapping("/post/{postId}")
    public void delete(@PathVariable long postId){
        service.delete(postId);
    }

    @PostMapping(value = "/post/{postId}/like")
    public ResponseEntity<PostDTO> likePostById(@PathVariable long postId,
                                                @RequestBody long userId){

        Optional<PostEntity> postEntity = service.likeOrUnlikePostById(postId, userId, Liking.LIKE);

        return postEntity.map(entity ->
                ResponseEntity.ok(postConverter.convertFromEntityToDTO(entity)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/post/{postId}/unlike")
    public ResponseEntity<PostDTO> unlikePostById(@PathVariable long postId,
                                                  @RequestBody long userId){

        Optional<PostEntity> postEntity = service.likeOrUnlikePostById(postId, userId, Liking.UNLIKE);

        return postEntity.map(entity ->
                        ResponseEntity.ok(postConverter.convertFromEntityToDTO(entity)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "post/{postId}/comment")
    public ResponseEntity<PostDTO> commentPostById(@PathVariable long postId,
                                                   @RequestBody CommentDTO comment){
        Optional<PostEntity> postEntity = service.commentPostById(postId, comment);

        return postEntity.map(entity ->
                ResponseEntity.ok(postConverter.convertFromEntityToDTO(entity)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "post/{postId}/comment/{commentId}")
    public ResponseEntity<PostDTO> deleteCommentById(@PathVariable long postId,
                                                    @PathVariable long commentId,
                                                    @RequestBody long userId){
        Optional<PostEntity> postEntity = service.deleteCommentById(postId, commentId, userId);

        return postEntity.map(entity ->
                        ResponseEntity.ok(postConverter.convertFromEntityToDTO(entity)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "post/{postId}/comment/{commentId}/like")
    public ResponseEntity<PostDTO> likeCommentById(@PathVariable long postId,
                                                     @PathVariable long commentId,
                                                     @RequestBody long userId){
        Optional<PostEntity> postEntity = service.likeOrUnlikeCommentById(postId, commentId, userId, Liking.LIKE);

        return postEntity.map(entity ->
                        ResponseEntity.ok(postConverter.convertFromEntityToDTO(entity)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "post/{postId}/comment/{commentId}/unlike")
    public ResponseEntity<PostDTO> unlikeCommentById(@PathVariable long postId,
                                                   @PathVariable long commentId,
                                                   @RequestBody long userId){
        Optional<PostEntity> postEntity = service.likeOrUnlikeCommentById(postId, commentId, userId, Liking.UNLIKE);

        return postEntity.map(entity ->
                        ResponseEntity.ok(postConverter.convertFromEntityToDTO(entity)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
