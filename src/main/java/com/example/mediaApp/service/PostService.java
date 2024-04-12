package com.example.mediaApp.service;

import com.example.mediaApp.converter.AppUserConverter;
import com.example.mediaApp.model.dto.LikeDTO;
import com.example.mediaApp.model.dto.PostDTO;
import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.model.entity.LikeEntity;
import com.example.mediaApp.model.entity.PostEntity;
import com.example.mediaApp.model.enums.PostLike;
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

    public Optional<PostEntity> likeOrUnlikePostById(long id, Long userId, PostLike method){
        PostEntity post = repository.findById(id).orElse(null);
        AppUserEntity user = userService.find(userId);

        if (post == null || user == null)
            return Optional.empty();

        LikeEntity like = getLikeByPostIdAndUserId(post, user);

        switch (method){
            case LIKE -> {
                if (like == null){
                    like = likeService.create(new LikeDTO(
                            0,
                            userConverter.convertFromEntityToDTO(user)
                    ));
                    post.getLikes().add(like);

                    repository.save(post);
                }
            }
            case UNLIKE -> {
                if (like != null){
                    likeService.delete(like);
                    post.getLikes().remove(like);

                    repository.save(post);
                }
            }
            case null, default -> {
                // Do nothing.
            }
        }

        return Optional.of(post);
    }

    private LikeEntity getLikeByPostIdAndUserId(PostEntity post, AppUserEntity user){
        return post.getLikes().stream()
                .filter(like -> like.getUser().equals(user))
                .findFirst()
                .orElse(null);
    }
}
