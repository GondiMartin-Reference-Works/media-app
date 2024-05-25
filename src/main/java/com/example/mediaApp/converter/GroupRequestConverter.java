package com.example.mediaApp.converter;

import com.example.mediaApp.model.dto.GroupRequestDTO;
import com.example.mediaApp.model.entity.GroupRequestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@DependsOn("postConverter")
public class GroupRequestConverter implements IGenericConverter<GroupRequestEntity, GroupRequestDTO> {

    private final AppUserConverter appUserConverter;
    private final PostConverter postConverter;

    @Override
    public GroupRequestDTO convertFromEntityToDTO(GroupRequestEntity entity) {
        GroupConverter groupConverter = new GroupConverter(
                appUserConverter,
                postConverter,
                this
        );

        return new GroupRequestDTO(
                entity.getId(),
                entity.getGroup() != null
                    ? groupConverter.convertFromEntityToDTO(entity.getGroup())
                    : null,
                entity.getSenderUser() != null
                    ? appUserConverter.convertFromEntityToDTO(entity.getSenderUser())
                    : null
        );
    }
}
