package com.example.mediaApp.converter;

import com.example.mediaApp.model.dto.CommentDTO;
import com.example.mediaApp.model.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentConverter implements IGenericConverter<CommentEntity, CommentDTO> {

    private final AppUserConverter appUserConverter;

    @Override
    public CommentDTO convertFromEntityToDTO(CommentEntity entity) {
        return new CommentDTO(
                entity.getText(),
                appUserConverter.convertFromEntityToDTO(entity.getUser())
        );
    }
}
