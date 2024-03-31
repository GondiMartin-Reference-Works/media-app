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
        //TODO: else part -> finding all like from likeService
        if (post.getComments() == null)
            newPost.setComments(new ArrayList<>());
        //TODO: else part -> finding all comment from commentService

        return newPost;
    }
}
