package com.example.mediaApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostDTO {
    private AppUserDTO user;
    private GroupDTO group;
    private String text;
    private byte[] image;
    private List<LikeDTO> likes;
    private List<CommentDTO> comments;
}