package com.example.mediaApp.converter;

import com.example.mediaApp.model.dto.FriendRequestDTO;
import com.example.mediaApp.model.entity.FriendRequestEntity;
import org.springframework.stereotype.Component;

@Component
public class FriendRequestConverter implements IGenericConverter<FriendRequestEntity, FriendRequestDTO> {
    @Override
    public FriendRequestDTO convertFromEntityToDTO(FriendRequestEntity entity) {
        return new FriendRequestDTO(
                entity.getId(),
                entity.getSenderUser().getId(),
                entity.getSenderUser().getEmail(),
                STR."\{entity.getSenderUser().getFirstName()} \{entity.getSenderUser().getLastName()}",
                entity.getReceiverUser().getId(),
                entity.getReceiverUser().getEmail());
    }
}
