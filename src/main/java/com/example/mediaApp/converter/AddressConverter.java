package com.example.mediaApp.converter;

import com.example.mediaApp.model.dto.AddressDTO;
import com.example.mediaApp.model.entity.AddressEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter implements IGenericConverter<AddressEntity, AddressDTO> {
    @Override
    public AddressDTO convertFromEntityToDTO(AddressEntity entity) {
        return new AddressDTO(
                entity.getId(),
                entity.getCountry(),
                entity.getCity(),
                entity.getZipCode(),
                entity.getStreet(),
                entity.getHouseNum()
        );
    }
}