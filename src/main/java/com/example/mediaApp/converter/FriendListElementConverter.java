package com.example.mediaApp.converter;

import com.example.mediaApp.model.dto.FriendListElementDTO;
import com.example.mediaApp.model.entity.FriendConnectionEntity;
import org.springframework.stereotype.Component;

@Component
public class FriendListElementConverter implements IGenericConverter<FriendConnectionEntity, FriendListElementDTO> {
    @Override
    public FriendListElementDTO convertFromEntityToDTO(FriendConnectionEntity entity) {
        return new FriendListElementDTO(
                entity.getFriend().getEmail(),
                entity.getFriend().getFirstName(),
                entity.getFriend().getLastName());
    }
}
