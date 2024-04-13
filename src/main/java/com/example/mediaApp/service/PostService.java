package com.example.mediaApp.service;

import com.example.mediaApp.converter.AppUserConverter;
import com.example.mediaApp.model.dto.CommentDTO;
import com.example.mediaApp.model.dto.LikeDTO;
import com.example.mediaApp.model.dto.PostDTO;
import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.model.entity.CommentEntity;
import com.example.mediaApp.model.entity.LikeEntity;
import com.example.mediaApp.model.entity.PostEntity;
import com.example.mediaApp.model.enums.Liking;
import com.example.mediaApp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
    private final AppUserService userService;
    private final GroupService groupService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final AppUserConverter userConverter;

    public List<PostEntity> getAll(){
        return repository.findAll();
    }

    public PostEntity create (PostDTO post){
        PostEntity newPost = new PostEntity();

        newPost.setUser( post.getUser() != null
                ? userService.find(post.getUser().getId())
                : null
        );
        newPost.setGroup( post.getGroup() != null
                ? groupService.find(post.getGroup().getId())
                : null
        );
        newPost.setText(post.getText());
        newPost.setImage(post.getImage());

        if (post.getLikes() == null)
            newPost.setLikes(new ArrayList<>());
        else
            newPost.setLikes(
                    post.getLikes().stream().map(like ->
                        likeService.get(like.getId())
                    ).toList()
            );

        if (post.getComments() == null)
            newPost.setComments(new ArrayList<>());
        else
            newPost.setComments(
                    post.getComments().stream().map(comment ->
                        commentService.get(comment.getId())
                    ).toList()
            );

        repository.save(newPost);

        return newPost;
    }

    public Optional<PostEntity> likeOrUnlikePostById(long id, Long userId, Liking method){
        PostEntity post = repository.findById(id).orElse(null);
        AppUserEntity user = userService.find(userId);

        if (post == null || user == null)
            return Optional.empty();

        LikeEntity like = getLikeByUserId(post.getLikes(), user);
        likeOrUnlike(method, user, like, post.getLikes());
        
        repository.save(post);

        return Optional.of(post);
    }

    public Optional<PostEntity> commentPostById(long postId, CommentDTO comment) {
        PostEntity post = repository.findById(postId).orElse(null);
        AppUserEntity user = userService.find(comment.getUser().getId());

        if (post == null || user == null)
            return Optional.empty();

        CommentEntity commentEntity = commentService.create(comment);
        post.getComments().add(commentEntity);

        repository.save(post);

        return Optional.of(post);
    }

    public Optional<PostEntity> deleteCommentById(long postId, long commentId, long userId) {
        PostEntity post = repository.findById(postId).orElse(null);
        CommentEntity comment = commentService.find(commentId);
        AppUserEntity user = userService.find(userId);

        if (post == null || comment == null || user == null)
            return Optional.empty();

        if (comment.getUser().equals(user)){
            commentService.delete(comment);
            post.getComments().remove(comment);

            repository.save(post);
        }

        return  Optional.of(post);
    }

    public void delete(long postId) {
        repository.deleteById(postId);
    }

    public Optional<PostEntity> likeOrUnlikeCommentById(long postId, long commentId, long userId, Liking method) {
        PostEntity post = repository.findById(postId).orElse(null);
        CommentEntity comment = commentService.find(commentId);
        AppUserEntity user = userService.find(userId);

        if (post == null || comment == null || user == null)
            return Optional.empty();

        LikeEntity like = getLikeByUserId(comment.getLikes(), user);
        likeOrUnlike(method, user, like, comment.getLikes());

        int commentIndex = post.getComments().indexOf(comment);
        post.getComments().set(commentIndex, comment);

        repository.save(post);

        return Optional.of(post);
    }

    private void likeOrUnlike(Liking method, AppUserEntity user, LikeEntity like, List<LikeEntity> likes) {
        switch (method){
            case LIKE -> {
                if (like == null){
                    like = likeService.create(new LikeDTO(
                            0,
                            userConverter.convertFromEntityToDTO(user)
                    ));
                    likes.add(like);
                }
            }
            case UNLIKE -> {
                if (like != null){
                    likeService.delete(like);
                    likes.remove(like);
                }
            }
        }
    }

    private LikeEntity getLikeByUserId(List<LikeEntity> likes, AppUserEntity user){
        return likes.stream()
                .filter(like -> like.getUser().equals(user))
                .findFirst()
                .orElse(null);
    }
}
