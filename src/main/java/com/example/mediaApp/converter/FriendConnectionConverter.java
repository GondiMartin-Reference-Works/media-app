package com.example.mediaApp.converter;

import com.example.mediaApp.model.dto.FriendConnectionDTO;
import com.example.mediaApp.model.entity.FriendConnectionEntity;
import org.springframework.stereotype.Component;

@Component
public class FriendConnectionConverter implements IGenericConverter<FriendConnectionEntity, FriendConnectionDTO> {
    @Override
    public FriendConnectionDTO convertFromEntityToDTO(FriendConnectionEntity entity) {
        return new FriendConnectionDTO(
                entity.getUser().getEmail(),
                entity.getFriend().getEmail()
        );
    }
}
