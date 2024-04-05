package com.example.mediaApp.converter;

import com.example.mediaApp.model.dto.GroupDTO;
import com.example.mediaApp.model.entity.GroupEntity;
import org.springframework.stereotype.Component;

@Component
public class GroupConverter implements IGenericConverter<GroupEntity, GroupDTO> {
    @Override
    public GroupDTO convertFromEntityToDTO(GroupEntity entity) {
        return new GroupDTO(
                entity.getId(),
                entity.getName()
        );
    }
}
