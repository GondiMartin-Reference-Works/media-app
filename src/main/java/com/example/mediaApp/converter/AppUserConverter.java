package com.example.mediaApp.converter;

import com.example.mediaApp.model.dto.AppUserDTO;
import com.example.mediaApp.model.entity.AppUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppUserConverter implements IGenericConverter<AppUserEntity, AppUserDTO> {

    private final AddressConverter addressConverter;
    @Override
    public AppUserDTO convertFromEntityToDTO(AppUserEntity entity) {
        return new AppUserDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getBirthDate(),
                entity.getProfilePicture(),
                entity.getAddresses()
                        .stream()
                        .map(addressConverter::convertFromEntityToDTO)
                        .toList()
        );
    }
}
