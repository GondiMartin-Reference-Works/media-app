package com.example.mediaApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentDTO {
    private long id;
    private String text;
    private AppUserDTO user;
    private List<LikeDTO> likes;
}
