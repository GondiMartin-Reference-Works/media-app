package com.example.mediaApp.service;

import com.example.mediaApp.model.dto.PostDTO;
import com.example.mediaApp.model.entity.PostEntity;
import com.example.mediaApp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
    private final AppUserService userService;
    private final GroupService groupService;
    private final LikeService likeService;
    private final CommentService commentService;

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
}
