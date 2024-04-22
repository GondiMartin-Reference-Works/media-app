package com.example.mediaApp.converter;

import com.example.mediaApp.model.dto.AppUserDTO;
import com.example.mediaApp.model.entity.AppUserEntity;
import org.springframework.stereotype.Component;

@Component

public class AppUserConverter implements IGenericConverter<AppUserEntity, AppUserDTO> {
    @Override
    public AppUserDTO convertFromEntityToDTO(AppUserEntity entity) {
        return new AppUserDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail()
        );
    }
}
