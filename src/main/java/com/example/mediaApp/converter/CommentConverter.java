package com.example.mediaApp.converter;

import com.example.mediaApp.model.dto.CommentDTO;
import com.example.mediaApp.model.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentConverter implements IGenericConverter<CommentEntity, CommentDTO> {

    private final AppUserConverter appUserConverter;
    private final LikeConverter likeConverter;

    @Override
    public CommentDTO convertFromEntityToDTO(CommentEntity entity) {
        return new CommentDTO(
                entity.getId(),
                entity.getText(),
                appUserConverter.convertFromEntityToDTO(entity.getUser()),
                entity.getLikes().stream()
                        .map(likeConverter::convertFromEntityToDTO)
                        .toList()
        );
    }
}
