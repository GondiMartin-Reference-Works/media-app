package com.example.mediaApp.converter;

import com.example.mediaApp.model.dto.*;
import com.example.mediaApp.model.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostConverter implements IGenericConverter<PostEntity, PostDTO>{

    private final AppUserConverter appUserConverter;
    private final GroupConverter groupConverter;
    private final LikeConverter likeConverter;
    private final CommentConverter commentConverter;

    public PostDTO convertFromEntityToDTO(PostEntity entity) {
        return new PostDTO(
                entity.getId(),
                entity.getUser() != null
                        ? appUserConverter.convertFromEntityToDTO(entity.getUser())
                        : null,
                entity.getGroup() != null
                        ? groupConverter.convertFromEntityToDTO(entity.getGroup())
                        : null,
                entity.getText(),
                entity.getImage(),
                entity.getLikes()
                        .stream()
                        .map(likeConverter::convertFromEntityToDTO)
                        .toList(),
                entity.getComments()
                        .stream()
                        .map(commentConverter::convertFromEntityToDTO)
                        .toList()
        );
    }
}
