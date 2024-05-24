package com.example.mediaApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupRequestDTO {
    private Long id;
    private GroupDTO group;
    private AppUserDTO senderUser;
}
