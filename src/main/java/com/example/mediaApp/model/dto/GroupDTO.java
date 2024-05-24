package com.example.mediaApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupDTO {
    private long id;
    private String name;
    private String description;
    private byte[] image;
    private AppUserDTO adminUser;
    private List<AppUserDTO> participantUsers;
    private List<GroupRequestDTO> groupRequest;
    private List<PostDTO> posts;
}
