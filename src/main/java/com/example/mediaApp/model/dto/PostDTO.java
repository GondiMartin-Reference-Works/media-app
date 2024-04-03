package com.example.mediaApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO {
    private AppUserDTO user;
    private GroupDTO group;
    private String text;
    private byte[] image;
    private List<LikeDTO> likes;
    private List<CommentDTO> comments;
}