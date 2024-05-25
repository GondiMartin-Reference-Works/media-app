package com.example.mediaApp.converter;

import com.example.mediaApp.model.dto.GroupDTO;
import com.example.mediaApp.model.entity.GroupEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GroupConverter implements IGenericConverter<GroupEntity, GroupDTO> {

    private AppUserConverter appUserConverter;
    private PostConverter postConverter;
    private GroupRequestConverter groupRequestConverter;

    @Override
    public GroupDTO convertFromEntityToDTO(GroupEntity entity) {
        return new GroupDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getImage(),
                entity.getAdminUser() != null
                    ? appUserConverter.convertFromEntityToDTO(entity.getAdminUser())
                    : null,
                entity.getParticipantUsers()
                    .stream()
                    .map(appUserConverter::convertFromEntityToDTO)
                    .toList(),
                entity.getGroupRequests()
                    .stream()
                    .map(groupRequestConverter::convertFromEntityToDTO)
                    .toList(),
                entity.getPosts()
                    .stream()
                    .map(postConverter::convertFromEntityToDTO)
                    .toList()
        );
    }
}
