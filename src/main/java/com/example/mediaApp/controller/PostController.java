package com.example.mediaApp.controller;

import com.example.mediaApp.converter.CommentConverter;
import com.example.mediaApp.converter.LikeConverter;
import com.example.mediaApp.converter.PostConverter;
import com.example.mediaApp.model.dto.CommentDTO;
import com.example.mediaApp.model.dto.LikeDTO;
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
@RequestMapping("/post")
@RequiredArgsConstructor
@CrossOrigin(origins = "${base.frontend.url}")
public class PostController{

    private final PostService service;
    private final PostConverter postConverter;
    private final LikeConverter likeConverter;
    private final CommentConverter commentConverter;

    @GetMapping()
    public ResponseEntity<List<PostDTO>> getAll(){
        return ResponseEntity.ok(service.getAll()
                .stream().map(postConverter::convertFromEntityToDTO)
                .toList()
                .reversed()
        );
    }

    @PostMapping()
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO post){
        PostEntity newPostEntity = service.create(post);
        PostDTO newPostDTO = postConverter.convertFromEntityToDTO(newPostEntity);
        return ResponseEntity.ok(newPostDTO);
    }

    @DeleteMapping()
    public void deleteAll(){
        service.deleteAll();
    }

    @DeleteMapping("/{postId}")
    public void delete(@PathVariable long postId){
        service.delete(postId);
    }

    @PostMapping(value = "/{postId}/like")
    public ResponseEntity<List<LikeDTO>> likePostById(@PathVariable long postId,
                                                      @RequestBody long userId){
        Optional<PostEntity> post = service.likeOrUnlikePostById(postId, userId, Liking.LIKE);

        return post.map(postEntity ->
                        ResponseEntity.ok(postEntity.getLikes().stream().map(likeConverter::convertFromEntityToDTO).toList()))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{postId}/unlike")
    public ResponseEntity<List<LikeDTO>> unlikePostById(@PathVariable long postId,
                                                        @RequestBody long userId){
        Optional<PostEntity> post = service.likeOrUnlikePostById(postId, userId, Liking.UNLIKE);

        return post.map(postEntity ->
                    ResponseEntity.ok(postEntity.getLikes().stream().map(likeConverter::convertFromEntityToDTO).toList()))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{postId}/comment")
    public ResponseEntity<CommentDTO> commentPostById(@PathVariable long postId,
                                                   @RequestBody CommentDTO comment){
        Optional<PostEntity> postEntity = service.commentPostById(postId, comment);

        return postEntity.map(entity ->
                ResponseEntity.ok(commentConverter.convertFromEntityToDTO(entity.getComments().getFirst())))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{postId}/comment/{commentId}")
    public ResponseEntity<PostDTO> deleteCommentById(@PathVariable long postId,
                                                    @PathVariable long commentId,
                                                    @RequestBody long userId){
        Optional<PostEntity> postEntity = service.deleteCommentById(postId, commentId, userId);

        return postEntity.map(entity ->
                        ResponseEntity.ok(postConverter.convertFromEntityToDTO(entity)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{postId}/comment/{commentId}/like")
    public ResponseEntity<List<CommentDTO>> likeCommentById(@PathVariable long postId,
                                                     @PathVariable long commentId,
                                                     @RequestBody long userId){
        Optional<PostEntity> post = service.likeOrUnlikeCommentById(postId, commentId, userId, Liking.LIKE);

        return post.map(postEntity ->
                    ResponseEntity.ok(postEntity.getComments().stream().map(commentConverter::convertFromEntityToDTO).toList()))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/{postId}/comment/{commentId}/unlike")
    public ResponseEntity<List<CommentDTO>> unlikeCommentById(@PathVariable long postId,
                                                   @PathVariable long commentId,
                                                   @RequestBody long userId){
        Optional<PostEntity> post = service.likeOrUnlikeCommentById(postId, commentId, userId, Liking.UNLIKE);

        return post.map(postEntity ->
                        ResponseEntity.ok(postEntity.getComments().stream().map(commentConverter::convertFromEntityToDTO).toList()))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
