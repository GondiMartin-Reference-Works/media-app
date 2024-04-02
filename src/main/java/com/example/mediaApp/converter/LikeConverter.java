package com.example.mediaApp.converter;

import com.example.mediaApp.model.dto.LikeDTO;
import com.example.mediaApp.model.entity.LikeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeConverter implements IGenericConverter<LikeEntity, LikeDTO> {

    private final AppUserConverter appUserConverter;

    @Override
    public LikeDTO convertFromEntityToDTO(LikeEntity entity) {
        return new LikeDTO(
          appUserConverter.convertFromEntityToDTO(entity.getUser())
        );
    }
}
