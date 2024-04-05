package com.example.mediaApp.converter;

import com.example.mediaApp.model.dto.AppFriendUserDTO;
import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.model.entity.FriendConnectionEntity;
import org.springframework.stereotype.Component;

@Component
public class AppFriendUserConverter implements IGenericConverter<AppUserEntity, AppFriendUserDTO> {

    private String email;

    public AppFriendUserConverter email(String email){
        this.email = email;
        return this;
    }

    @Override
    public AppFriendUserDTO convertFromEntityToDTO(AppUserEntity entity) {
        return new AppFriendUserDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getFriendConnections().stream()
                        .map(FriendConnectionEntity::getFriend)
                        .map(AppUserEntity::getEmail)
                        .anyMatch(email::equals));
    }
}
