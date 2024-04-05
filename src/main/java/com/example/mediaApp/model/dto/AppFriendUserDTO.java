package com.example.mediaApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppFriendUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isFriend;
}
